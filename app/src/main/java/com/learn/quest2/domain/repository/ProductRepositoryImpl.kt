package com.learn.quest2.domain.repository

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.learn.quest2.data.api.ProductAPI
import com.learn.quest2.data.dao.ProductDao
import com.learn.quest2.data.entity.ProductEntity
import com.learn.quest2.data.model.Product
import com.learn.quest2.data.model.ProductResponse
import com.learn.quest2.helper.Converter.toProductEntityList
import com.learn.quest2.helper.Converter.toProductList
import com.learn.quest2.presentation.state.Category
import com.learn.quest2.presentation.state.PriceRange
import com.learn.quest2.presentation.state.QueryFilter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productAPI: ProductAPI,
    private val productDao: ProductDao
) : ProductRepository {
    val TAG = javaClass.simpleName

    override suspend fun insertAllProductsFromDB(listOfProductEntity: List<ProductEntity>) {
        productDao.insertAllProduct(listOfProductEntity)
    }

    override suspend fun getAllProducts(): Result<ProductResponse> {
        return productAPI.runCatching { getAllProducts() }
    }

    override suspend fun searchedProductsFromApi(searchTerm: String): Flow<List<ProductEntity>> =
        flow {
            productAPI.runCatching { getSearchedProducts(searchTerm) }.mapCatching {
                emit(it.products.toProductEntityList())
            }.onFailure { error ->
                emit(emptyList())
                Log.e(TAG, "getSearchedProductsFromApi: $error")
            }
        }

    override suspend fun searchedProductsFromDB(searchTerm: String): Flow<List<ProductEntity>> {
        return productDao.getSearchedProduct(searchTerm)
    }

    override suspend fun searchAndStoreProduct(searchTerm: String): Flow<List<ProductEntity>> =
        flow {
            val apiResult = searchedProductsFromApi(searchTerm)
            val dbFlow = searchedProductsFromDB(searchTerm)

            merge(
                apiResult,
                dbFlow
            ).collect { apiProducts ->
               emit(apiProducts.distinctBy { it.id })
            }
        }

    override suspend fun getAllProductsFromDB(): Flow<List<ProductEntity>> {
        return productDao.getAllProductInAscendingOrder()
    }

    override suspend fun fetchAndStoreProduct(): Result<List<Product>> {
        return getAllProducts()
            .fold(
                onSuccess = { productList ->
                    if (productList.products.isNotEmpty()) {
                        insertAllProductsFromDB(productList.products.toProductEntityList())
                    }
                    Result.success(productList.products)
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
    }


    override suspend fun getAllProductsFromDBWithFilter(
        filter: QueryFilter
    ): Flow<List<ProductEntity>> {
        val baseQuery = "Select * from Product"

        val query = when {
            filter.priceRange != PriceRange.ALL && filter.category != Category.ALL ->
                SimpleSQLiteQuery("$baseQuery ORDER BY title ${filter.sortOrder.value}")

            filter.priceRange != PriceRange.ALL ->
                SimpleSQLiteQuery("$baseQuery where price <= ${filter.priceRange.value} ORDER BY title ${filter.sortOrder.value}")

            filter.category != Category.ALL ->
                SimpleSQLiteQuery("$baseQuery where category == '${filter.category.value}' ORDER BY title ${filter.sortOrder.value}")

            else -> {
                SimpleSQLiteQuery("$baseQuery ORDER BY title ${filter.sortOrder.value}")
            }
        }
        return productDao.getAllProductsSorted(query)
    }

    override suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }
}