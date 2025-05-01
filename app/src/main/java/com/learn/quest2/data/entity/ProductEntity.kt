package com.learn.quest2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learn.quest2.data.model.Dimensions
import com.learn.quest2.data.model.Meta
import com.learn.quest2.data.model.ReviewsItem

@Entity(tableName = "Product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
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
    val price: Double? = 0.0,
    val shippingInformation: String? = "",
    val availabilityStatus: String? = "",
    val category: String? = "",
    val stock: Int? = 0,
    val sku: String? = "",
    val brand: String? = "",
    val meta: Meta,
    val reviews: List<ReviewsItem>?,
    val images: List<String>?,
    val dimensions: Dimensions
)