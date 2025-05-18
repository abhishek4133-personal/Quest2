package com.learn.quest2.domain.repository

import com.learn.quest2.presentation.model.Order
import com.learn.quest2.presentation.model.User

interface ECommerceRepository {

    fun getListOfUsers(): List<User>

    fun getListOfOrders(): List<Order>
}