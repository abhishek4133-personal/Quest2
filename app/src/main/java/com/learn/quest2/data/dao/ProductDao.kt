package com.learn.quest2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SimpleSQLiteQuery
import com.learn.quest2.data.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun insertAllProduct(posts: List<ProductEntity>)

    @Query("DELETE FROM Product")
    suspend fun deleteAllProducts()

    @RawQuery(observedEntities = [ProductEntity::class])
    fun getAllProductsSorted(query: SimpleSQLiteQuery): Flow<List<ProductEntity>>

    @Query("Select * from Product where id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Query("Select * from Product ORDER BY title ASC")
    fun getAllProductInAscendingOrder(): Flow<List<ProductEntity>>

    @Query("Select * from Product ORDER BY title DESC")
    fun getAllProductInDescendingOrder(): Flow<List<ProductEntity>>

    @Query("Select * from Product where category = :category and price <= :price ORDER BY :sortOrder ASC")
    fun getAllProductWithPriceAndCategoryFilter(
        category: String,
        price: Double,
        sortOrder: String
    ): Flow<List<ProductEntity>>

    @Query("Select * from Product where price <= :price ORDER BY title ASC")
    fun getAllProductWithPriceFilter(price: Double): Flow<List<ProductEntity>>

    @Query("Select * from Product where category = :category ORDER BY title ASC")
    fun getAllProductWithCategoryFilter(category: String): Flow<List<ProductEntity>>

    @Query("Select * from Product where category LIKE '%' || :searchTerm || '%' OR title LIKE '%' || :searchTerm || '%' OR description LIKE '%' || :searchTerm || '%' ORDER BY title ASC")
    fun getSearchedProduct(searchTerm: String): Flow<List<ProductEntity>>
}
