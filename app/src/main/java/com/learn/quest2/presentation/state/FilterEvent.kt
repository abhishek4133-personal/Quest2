package com.learn.quest2.presentation.state

sealed class FilterEvent {
    data class OnSortingOrderChanged(val sortOrder: SortOrder) : FilterEvent()
    data class OnPriceRangeChanged(val priceRange: PriceRange) : FilterEvent()
    data class OnCategoryChanged(val category: Category) : FilterEvent()
}