package com.learn.quest2.quest4.model.domain

import com.learn.quest2.quest4.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUserById(userId: String): User?
    suspend fun fetchAllUsers(): Flow<User>
    suspend fun saveUser(user: User): User
    suspend fun deleteUser(userId: String): Boolean
}


