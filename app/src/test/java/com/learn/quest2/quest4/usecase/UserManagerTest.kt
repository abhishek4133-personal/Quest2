package com.learn.quest2.usecase

import com.google.common.truth.Truth.assertThat
import com.learn.quest2.quest4.data.cache.UserCacheImpl
import com.learn.quest2.quest4.model.User
import com.learn.quest2.quest4.model.domain.UserRepository
import com.learn.quest2.quest4.usecase.UserManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserManagerTest {

    private var memoryCache: UserCacheImpl = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk()
    private lateinit var userManager: UserManager
    private lateinit var testUser: User

    @Before
    fun setUp() {
        userManager = UserManager(userRepository, memoryCache)
        testUser = User("123", "abhishek", "abhi@gmail.com")
    }

    @Test
    fun `getUser user when not available in cache`() = runTest {
        coEvery { memoryCache.getUser("129") } returns null

        // fetching user from cache
        val fetcherUser = memoryCache.getUser("129")
        assertNull(fetcherUser)
    }

    @Test
    fun `getUser when available`() = runTest {
        coEvery { memoryCache.getUser("123") } returns testUser

        val fetcherUser = memoryCache.getUser("123")

        assertThat(testUser).isEqualTo(fetcherUser)
    }


    @Test
    fun `getUser fetches from repo if not cached`() = runTest {
        coEvery { memoryCache.getUser("1234") } returns null
        coEvery { userRepository.fetchUserById("1234") } returns testUser
        coEvery { memoryCache.putUser(testUser) } just Runs

        val fetcherUser = userManager.getUser("1234")

        assertThat(testUser).isEqualTo(fetcherUser)
        coVerify { memoryCache.putUser(testUser) }
    }

    @Test
    fun `getUser returns null if not found in cache or repo`() = runTest {
        coEvery { memoryCache.getUser("123") } returns null
        coEvery { userRepository.fetchUserById("123") } returns null

        val fetcherUser = userManager.getUser("123")

        assertNull(fetcherUser)
    }

    @Test
    fun `saveUser should saves user in cache and repo both`() = runTest {
        coEvery { userRepository.saveUser(testUser) } returns testUser
        coEvery { memoryCache.putUser(testUser) } just Runs

        val savedUser = userManager.saveUser(testUser)

        assertThat(testUser).isEqualTo(savedUser)
        coVerify { memoryCache.putUser(testUser) }
    }

    @Test
    fun `deleteUser should removes user from cache and repo both`() = runTest {
        coEvery { userRepository.deleteUser("123") } returns true
        coEvery { memoryCache.removeUser("123") } just Runs

        val result = userManager.deleteUser("123")

        assertTrue(result)
        coVerify { userRepository.deleteUser("123") }
        coVerify { memoryCache.removeUser("123") }
    }

    @Test
    fun `deleteUser returns false if not deleted from repo`() = runTest {
        coEvery { userRepository.deleteUser("123") } returns false

        val result = userManager.deleteUser("123")

        assertFalse(result)
        coVerify(exactly = 0) { memoryCache.removeUser(any()) }
    }
}