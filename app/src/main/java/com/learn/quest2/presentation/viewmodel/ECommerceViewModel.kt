package com.learn.quest2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.learn.quest2.domain.repository.ECommerceRepository
import com.learn.quest2.domain.usecase.OrderAnalysis
import com.learn.quest2.presentation.state.ECommerceViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ECommerceViewModel @Inject constructor(
    repository: ECommerceRepository,
    private val orderAnalysis: OrderAnalysis
) : ViewModel() {

    val TAG = javaClass.simpleName
    private val _uiState = MutableStateFlow(ECommerceViewModelState())
    val uiState = _uiState.asStateFlow()

    init {
        /*
         IMPORTANT
         {repository.getListOfOrders()} generates random data each time we call it, that is the reason to keep it in init,
         so that same data will be available in every stats building and passing it to useCase
         instead of getting directly in useCase from repo
         */

        val userList = repository.getListOfUsers()
        val orderList = repository.getListOfOrders()

        val totalRevenue = orderAnalysis.calculateTotalRevenue(
            orders = orderList
        )
        _uiState.update {
            it.copy(
                userList = userList,
                orderList = orderList,
                totalRevenue = totalRevenue,
            )
        }
    }

    fun getAllStatistics() {
        val userList = _uiState.value.userList
        val orderList = _uiState.value.orderList

        val expensiveOrder = orderAnalysis.findExpensiveOrder(
            orders = orderList
        )
        val uniqueProductIds = orderAnalysis.getAllUniqueProductIds(
            orders = orderList
        )
        val productSaleCount = orderAnalysis.getProductSaleCount(
            orders = orderList
        )
        val spendingForEachUser = orderAnalysis.getSpendingForEachUser(
            orders = orderList,
            users = userList
        )

        //calculating loyalty points for first user only
        val trackLoyaltyPoints = orderAnalysis.calculateLoyaltyPoints(
            user = userList.first(),
            orders = orderList
        )

        _uiState.update {
            it.copy(
                userList = it.userList,
                orderList = it.orderList,
                totalRevenue = it.totalRevenue,
                expensiveOrder = expensiveOrder,
                uniqueProductIds = uniqueProductIds,
                productSaleCount = productSaleCount,
                spendingForEachUser = spendingForEachUser,
                loyaltyPoints = trackLoyaltyPoints,
            )
        }
    }
}