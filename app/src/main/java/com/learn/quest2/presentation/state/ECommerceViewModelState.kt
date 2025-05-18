package com.learn.quest2.presentation.state

import com.learn.quest2.presentation.model.Order
import com.learn.quest2.presentation.model.OrderItems
import com.learn.quest2.presentation.model.User
import java.math.BigDecimal

data class ECommerceViewModelState(
    val userList: List<User> = emptyList(),
    val orderList: List<Order> = emptyList(),
    val expensiveOrder: OrderItems? = null,
    val uniqueProductIds: Set<String>? = null ,
    val totalRevenue: BigDecimal = BigDecimal.ZERO,
    val productSaleCount: Map<String, Int>? = null,
    val spendingForEachUser: Map<String, BigDecimal>? = null,
    val loyaltyPoints: List<Int>? = null,
)
