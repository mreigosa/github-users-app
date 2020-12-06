package com.mreigar.githubusers.network.datasource

import com.mreigar.githubusers.network.api.GithubApi
import com.mreigar.githubusers.network.api.NetworkApi
import com.mreigar.githubusers.network.exception.NetworkException
import com.mreigar.githubusers.network.mock.MockServer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest

class GithubRemoteDataSourceImplTest : AutoCloseKoinTest() {

    private lateinit var postApi: GithubApi
    private lateinit var mockServer: MockServer

    @Before
    fun before() {
        mockServer = MockServer.create()
        postApi = NetworkApi().provideApi(mockServer.start(), GithubApi::class.java)

        startKoin {
            modules(
                listOf(
                    module {
                        single(override = true) { postApi }
                    }
                )
            )
        }
    }

    @After
    fun after() {
        mockServer.shutdown()
    }

    @Test
    fun `given success response user is retrieved`() {
        mockServer.enqueueFile("getUser.json")

        runBlocking {
            val response = GithubRemoteDataSourceImpl(postApi).getUser("test-username")

            assertThat(response).isNotNull
            assertThat(response.username).isNotEmpty()
        }
    }

    @Test(expected = NetworkException::class)
    fun `when user not found, we receive expected exception`() {
        mockServer.enqueue(404, "notFoundError.json")

        runBlocking {
            GithubRemoteDataSourceImpl(postApi).getUser("test-username")
        }
    }

    @Test(expected = NetworkException::class)
    fun `that cannot fetch user`() {
        mockServer.enqueue(500)

        runBlocking {
            GithubRemoteDataSourceImpl(postApi).getUser("test-username")
        }
    }

    @Test
    fun `given success response repos are retrieved`() {
        mockServer.enqueueFile("getRepositories.json")

        runBlocking {
            val response = GithubRemoteDataSourceImpl(postApi).getUserRepos("test-username")

            assertThat(response).isNotNull
            assertThat(response).isNotEmpty()
        }
    }

    @Test(expected = NetworkException::class)
    fun `when user not found in repos request, we receive expected exception`() {
        mockServer.enqueue(404, "notFoundError.json")
        runBlocking {
            GithubRemoteDataSourceImpl(postApi).getUserRepos("test-username")
        }
    }

    @Test(expected = NetworkException::class)
    fun `that cannot fetch repos`() {
        runBlocking {
            mockServer.enqueue(500)
            GithubRemoteDataSourceImpl(postApi).getUserRepos("test-username")
        }
    }
}