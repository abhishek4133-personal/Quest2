package com.learn.quest2.data.model

data class ReviewsItem(
    val date: String? = "",
    val reviewerName: String? = "",
    val reviewerEmail: String? = "",
    val rating: Int? = 0,
    val comment: String? = ""
)