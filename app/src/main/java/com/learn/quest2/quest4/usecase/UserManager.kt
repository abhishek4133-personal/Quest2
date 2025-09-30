package com.learn.quest2.quest4.usecase

import com.learn.quest2.quest4.model.User
import com.learn.quest2.quest4.model.domain.UserCache
import com.learn.quest2.quest4.model.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow


class UserManager(
    private val userRepository: UserRepository,
    private val userCache: UserCache
) {
    suspend fun getUser(userId: String): User? {
        var user = userCache.getUser(userId)
        user?.let {
            return it
        } ?: run {
            user = userRepository.fetchUserById(userId)
            user?.let { userCache.putUser(it) }
            return user
        }
    }

    suspend fun refreshAllUsers(): Flow<User> {
        return userRepository.fetchAllUsers().also { flow ->
            flow.collect { user ->
                userCache.putUser(user)
            }
        }

    }

    fun observeAllUsers(): SharedFlow<List<User>> {
        return userCache.observeUsers()
    }

    suspend fun saveUser(user: User): User {
        return userRepository.saveUser(user).also { userData ->
            userCache.putUser(userData)
        }
    }

    suspend fun deleteUser(userId: String): Boolean {
        return userRepository.deleteUser(userId).also { isDeleted ->
            if (isDeleted) {
                userCache.removeUser(userId)
            }
        }
    }
}
