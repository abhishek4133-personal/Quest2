package com.learn.quest2.domain.usecase

import com.learn.quest2.presentation.model.Order
import com.learn.quest2.presentation.model.OrderItems
import com.learn.quest2.presentation.model.User
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class OrderAnalysis @Inject constructor() {
    fun calculateTotalRevenue(orders: List<Order>): BigDecimal {
        return orders.fold(BigDecimal.ZERO) { acc, order ->
            acc + order.items.sumOf { it.price * it.quantity.toBigDecimal() }
        }
    }

    fun findExpensiveOrder(orders: List<Order>): OrderItems {
        val flatMapData =
            orders.flatMap { it.items }
                .distinctBy { it.productId }
                .sortedBy { it.price }
                .reduce { acc, orderItem ->
                    if (orderItem.price > acc.price) {
                        orderItem
                    } else {
                        acc
                    }
                }

        return flatMapData
    }

    fun getAllUniqueProductIds(orders: List<Order>): Set<String> {
        return orders.flatMap { it.items }.map { it.productId }.toSet()
    }

    fun getProductSaleCount(orders: List<Order>): Map<String, Int> {
        val productSaleCount = mutableMapOf<String, Int>()
        orders.flatMap { it.items }.forEach { orderItem ->
            productSaleCount[orderItem.productId] =
                (productSaleCount[orderItem.productId] ?: 0) + orderItem.quantity
        }
        return productSaleCount
    }

    fun getSpendingForEachUser(orders: List<Order>, users: List<User>): Map<String, BigDecimal> {
        val userSpending = mutableMapOf<String, BigDecimal>()

        users.associateBy { it.userId }.forEach { user ->
            val spentByUser = orders.filter { it.userId == user.key }
                .map { it.items }
                .fold(BigDecimal.ZERO) { acc, order ->
                    acc + order.sumOf { it.price * it.quantity.toBigDecimal() }
                }
            userSpending[user.value.username] = spentByUser
        }
        return userSpending
    }

    fun calculateLoyaltyPoints(user: User, orders: List<Order>): List<Int> {
        val spentByUser = orders.filter { it.userId == user.userId }
            .map { it.items }
            .scan(user.loyaltyPoints) { acc, order ->
                acc + order.sumOf {
                    it.price.setScale(0, RoundingMode.DOWN).toInt() * it.quantity
                }
            }
        return spentByUser
    }
}

