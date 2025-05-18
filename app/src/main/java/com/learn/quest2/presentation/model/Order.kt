package com.learn.quest2.presentation.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val orderId: String,
    val userId: String,
    val items: List<OrderItems>,
    val timestamp: LocalDateTime,
    val totalOrderValue: BigDecimal
)
