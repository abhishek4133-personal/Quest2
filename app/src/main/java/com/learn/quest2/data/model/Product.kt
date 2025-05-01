package com.learn.quest2.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    val images: List<String>?,
    val thumbnail: String? = "",
    val minimumOrderQuantity: Int? = 0,
    val rating: Double? = 0.0,
    val returnPolicy: String? = "",
    val description: String? = "",
    val weight: Int? = 0,
    val warrantyInformation: String? = "",
    val title: String? = "",
    val tags: List<String>?,
    val discountPercentage: Double? = 0.0,
    val reviews: List<ReviewsItem>?,
    val price: Double? = 0.0,
    val meta: Meta,
    val shippingInformation: String? = "",
    val id: Int? = 0,
    val availabilityStatus: String? = "",
    val category: String? = "",
    val stock: Int? = 0,
    val sku: String? = "",
    val brand: String? = "",
    val dimensions: Dimensions
)

data class ProductResponse(
    @SerializedName("products")
    val products: List<Product>,

    @SerializedName("total")
    val total: Int = 0,

    @SerializedName("skip")
    val skip: Int = 0,

    @SerializedName("limit")
    val limit: Int = 0
)
