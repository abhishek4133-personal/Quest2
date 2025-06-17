package com.learn.quest2.quest4.domain

import com.google.common.truth.Truth.assertThat
import com.learn.quest2.quest4.data.repo.UserRepositoryImpl
import com.learn.quest2.quest4.model.User
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl
    private val testUser = User("123", "Abhishek", "abhi@gmail.com")

    @Before
    fun setup() {
        repository = UserRepositoryImpl()
    }

    @Test
    fun `saveUser stores and returns the user`() = runTest {
        val result = repository.saveUser(testUser)

        assertThat(result).isEqualTo(testUser)
    }

    @Test
    fun `fetchUserById returns user if exists`() = runTest {
        repository.saveUser(testUser)

        val result = repository.fetchUserById(testUser.id)

        assertThat(result).isEqualTo(testUser)
    }

    @Test
    fun `fetchUserById returns null if user does not exist`() = runTest {
        val result = repository.fetchUserById("123456")

        assertThat(result).isNull()
    }

    @Test
    fun `fetchAllUsers returns all saved users`() = runTest {
        val user2 = User("127", "raju", "raju@gmail.com")
        repository.saveUser(testUser)
        repository.saveUser(user2)

        val users = repository.fetchAllUsers().toList()

        assertThat(users).containsExactly(testUser, user2)
    }

    @Test
    fun `deleteUser deletes user and returns true if successful`() = runTest {
        repository.saveUser(testUser)

        val deleted = repository.deleteUser(testUser.id)

        assertThat(deleted).isTrue()
        val user = repository.fetchUserById(testUser.id)
        assertThat(user).isNull()
    }

    @Test
    fun `deleteUser returns false if user not found`() = runTest {
        val deleted = repository.deleteUser("125")

        assertThat(deleted).isFalse()
    }
}
