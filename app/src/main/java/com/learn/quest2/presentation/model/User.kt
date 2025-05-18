package com.learn.quest2.presentation.model

data class User(
    val userId: String,
    val username: String,
    val loyaltyPoints: Int = 0,
)
