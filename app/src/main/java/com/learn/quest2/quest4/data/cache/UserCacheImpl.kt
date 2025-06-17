package com.learn.quest2.quest4.data.cache

import com.learn.quest2.quest4.model.User
import com.learn.quest2.quest4.model.domain.UserCache
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserCacheImpl : UserCache {
    private val mutex = Mutex()
    private val userCache = mutableMapOf<String, User>() // cache data holder
    private val userDataFlow = MutableSharedFlow<List<User>>(replay = 1)

    override suspend fun getUser(userId: String): User? = mutex.withLock {
        userCache[userId]
    }

    override suspend fun putUser(user: User) = mutex.withLock {
        userCache[user.id] = user.also {
            userDataFlow.emit(userCache.values.toList())
        }
    }

    override suspend fun removeUser(userId: String) {
        userCache.remove(userId).also {
            userDataFlow.emit(userCache.values.toList())
        }
    }

    override fun observeUsers(): SharedFlow<List<User>> = userDataFlow.asSharedFlow()

    override suspend fun initializeCache(users: List<User>) {
        mutex.withLock {
            userCache.clear()
            users.forEach { user ->
                userCache[user.id] = user
            }
            userDataFlow.emit(userCache.values.toList())
        }
    }
}