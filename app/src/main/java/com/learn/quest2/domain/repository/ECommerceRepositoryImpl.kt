package com.learn.quest2.domain.repository

import com.learn.quest2.presentation.model.Order
import com.learn.quest2.presentation.model.OrderItems
import com.learn.quest2.presentation.model.User
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class ECommerceRepositoryImpl @Inject constructor() : ECommerceRepository {
    override fun getListOfUsers(): List<User> {
        val userName = listOf(
            "John",
            "Jane",
            "Alice",
            "Bob",
            "Charlie"
        )
        val users = (0..4).map {
            User(
                userId = "user_id_$it",
                username = userName[it],
                loyaltyPoints = (0..100).random(),
            )
        }
        return users
    }

    override fun getListOfOrders(): List<Order> {
        val orders = (1..25).map { orderCount ->
            var totalOrderValue = 0.0

            // Generate random order items
            val randomOrderItemsCount = (1..5).random()
            val orderItems = mutableListOf<OrderItems>()
            repeat(randomOrderItemsCount) {
                val randomPrice = Random.nextDouble(9.9, 99.9).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN)

                val quantity = (1..10).random()
                totalOrderValue += randomPrice.toDouble() * quantity

                orderItems.add(OrderItems(
                    productId = "product_id_$it",
                    quantity = quantity,
                    price = randomPrice,
                ))
            }

            Order(
                orderId = "order_${UUID.randomUUID()}",
                userId = getListOfUsers().random().userId,
                items = orderItems,
                totalOrderValue = totalOrderValue.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN),
                timestamp = LocalDateTime.now().minusDays((1..7).random().toLong())
            )
        }
        return orders
    }
}