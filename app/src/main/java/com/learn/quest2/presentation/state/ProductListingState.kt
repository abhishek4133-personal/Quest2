package com.learn.quest2.presentation.state

import com.learn.quest2.data.model.Product

data class ProductListingState(
    val products: List<Product> = emptyList(),
    val error: String = ""
)

data class QueryFilter(
    val searchTerm: String = "",
    val category: Category = Category.ALL,
    val priceRange: PriceRange = PriceRange.ALL,
    val sortOrder: SortOrder = SortOrder.ASCENDING
)

enum class Category(val value: String) {
    ALL("all"),
    BEAUTY("beauty"),
    FRAGRANCE("fragrances"),
    FURNITURE("furniture"),
    GROCERIES("groceries"),
}

enum class PriceRange(val value: Double) {
    ALL(0.0),
    LESS_THAN_5(5.0),
    LESS_THAN_10(10.0),
    LESS_THAN_50(50.0),
    LESS_THAN_100(100.0),
    LESS_THAN_1000(1000.0)
}

enum class SortOrder(val value: String) {
    ASCENDING("ASC"),
    DESCENDING("DESC")
}


