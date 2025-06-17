package com.learn.quest2.quest4.model.domain

import com.learn.quest2.quest4.model.User
import kotlinx.coroutines.flow.SharedFlow


interface UserCache {
    suspend fun getUser(userId: String): User?
    suspend fun putUser(user: User)
    suspend fun removeUser(userId: String)
    fun observeUsers(): SharedFlow<List<User>>
    suspend fun initializeCache(users: List<User>)
}
