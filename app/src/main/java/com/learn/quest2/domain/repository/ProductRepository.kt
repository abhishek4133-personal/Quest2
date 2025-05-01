package com.learn.quest2.domain.repository

import com.learn.quest2.data.entity.ProductEntity
import com.learn.quest2.data.model.Product
import com.learn.quest2.data.model.ProductResponse
import com.learn.quest2.presentation.state.QueryFilter
import kotlinx.coroutines.flow.Flow


interface ProductRepository {

    suspend fun getAllProducts(): Result<ProductResponse>

    suspend fun deleteAllProducts()

    suspend fun searchedProductsFromApi(searchTerm: String): Flow<List<ProductEntity>>

    suspend fun searchAndStoreProduct(searchTerm: String): Flow<List<ProductEntity>>

    suspend fun fetchAndStoreProduct(): Result<List<Product>>

    suspend fun getAllProductsFromDB(): Flow<List<ProductEntity>>

    suspend fun insertAllProductsFromDB(listOfProductEntity: List<ProductEntity>)

    suspend fun getAllProductsFromDBWithFilter(filter: QueryFilter): Flow<List<ProductEntity>>

    suspend fun searchedProductsFromDB(searchTerm: String): Flow<List<ProductEntity>>
}