package com.learn.quest2.data.api

import com.learn.quest2.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPI {
    @GET("products")
    suspend fun getAllProducts(): ProductResponse

    @GET("products/search")
    suspend fun getSearchedProducts(@Query("q") searchTerm: String): ProductResponse
}