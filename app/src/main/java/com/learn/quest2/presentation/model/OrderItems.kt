package com.learn.quest2.presentation.model

import java.math.BigDecimal

data class OrderItems(
    val productId: String,
    val quantity: Int,
    val price: BigDecimal,
)
