package com.learn.quest2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.quest2.domain.repository.ProductRepository
import com.learn.quest2.helper.Converter.toProductList
import com.learn.quest2.presentation.state.Category
import com.learn.quest2.presentation.state.FilterEvent
import com.learn.quest2.presentation.state.PriceRange
import com.learn.quest2.presentation.state.ProductState
import com.learn.quest2.presentation.state.QueryFilter
import com.learn.quest2.presentation.state.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductListingViewModel @Inject constructor(
    private val productRepository: ProductRepository
    ) : ViewModel() {

    val TAG = javaClass.simpleName
    private val _uiState = MutableStateFlow(ProductState())

    private val _filterTypeState = MutableStateFlow(QueryFilter())
    val filterTypeState = _filterTypeState.asStateFlow()

    @OptIn(FlowPreview::class)
    private val _searchResults = _filterTypeState
        .debounce(500)
        .flatMapLatest { filter ->
            if (filter.searchTerm.isNotEmpty()) {
                productRepository.searchAndStoreProduct(filter.searchTerm)
            } else {
                flowOf(emptyList())
            }
        }

    private val _productList = _filterTypeState
        .flatMapLatest { filter ->
            productRepository.getAllProductsFromDBWithFilter(filter)
        }

    val state = combine(
        _uiState,
        _searchResults,
        _productList
    ) { uiListingState, searchResults, allProducts ->
        uiListingState.copy(
            products = if (searchResults.isNotEmpty()) {
                searchResults.toProductList()
            } else {
                allProducts.toProductList()
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            productRepository.fetchAndStoreProduct().fold(
                onSuccess = { products ->
                    Log.d(TAG, "Products fetched successfully: $products")
                },
                onFailure = { error ->
                    Log.e(TAG, "Products fetched failed: $error")
                }
            )
        }
    }

    fun onSearch(searchTerm: String) {
        _filterTypeState.update { currentState ->
            currentState.copy(
                searchTerm = searchTerm,
                category = Category.ALL,
                priceRange = PriceRange.ALL,
            )
        }
    }

    fun onQueryChanged(filterEvent: FilterEvent) {
        when(filterEvent) {
            is FilterEvent.OnSortingOrderChanged -> {
                _filterTypeState.update { currentState ->
                    currentState.copy(
                        sortOrder = filterEvent.sortOrder
                    )
                }
            }
            is FilterEvent.OnPriceRangeChanged -> {
                _filterTypeState.update { currentState ->
                    currentState.copy(
                        priceRange = filterEvent.priceRange,
                    )
                }
            }
            is FilterEvent.OnCategoryChanged -> {
                _filterTypeState.update { currentState ->
                    currentState.copy(
                        category =  filterEvent.category,
                    )
                }
            }
        }
    }

    fun onManualRefresh() {
        viewModelScope.launch {
            productRepository.deleteAllProducts()
            getAllProducts()
        }
    }
}