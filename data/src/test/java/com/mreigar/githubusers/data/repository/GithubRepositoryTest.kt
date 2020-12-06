package com.mreigar.githubusers.data.repository

import com.mreigar.githubusers.data.mock.RemoteDataSourceStatus
import com.mreigar.githubusers.data.mock.RepositoryInstrument.givenGithubRemoteDataSource
import com.mreigar.githubusers.domain.executor.Error
import com.mreigar.githubusers.domain.executor.Success
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GithubRepositoryTest {

    @Test
    fun `that can get user from network`() {
        val repository = GithubRepository(
            givenGithubRemoteDataSource(status = RemoteDataSourceStatus.SUCCESS),
        )

        runBlocking {
            val result = repository.getUser("username")

            assertThat(result is Success).isTrue()
            assertThat((result as Success).data).isNotNull()
        }
    }

    @Test
    fun `that error is returned from handler when error received getting user`() {
        val repository = GithubRepository(
            givenGithubRemoteDataSource(status = RemoteDataSourceStatus.ERROR),
        )

        runBlocking {
            val result = repository.getUser("username")

            assertThat(result is Error).isTrue()
        }
    }

    @Test
    fun `that can get repos from network`() {
        val repository = GithubRepository(
            givenGithubRemoteDataSource(status = RemoteDataSourceStatus.SUCCESS),
        )

        runBlocking {
            val result = repository.getGithubRepos("username")

            assertThat(result is Success).isTrue()
            assertThat((result as Success).data).isNotNull()
            assertThat((result).data).isNotEmpty()
        }
    }

    @Test
    fun `that empty list is returned when repos from remote are empty`() {
        val repository = GithubRepository(
            givenGithubRemoteDataSource(status = RemoteDataSourceStatus.SUCCESS, repos = emptyList()),
        )

        runBlocking {
            val result = repository.getGithubRepos("username")

            assertThat(result is Success).isTrue()
            assertThat((result as Success).data).isNotNull()
            assertThat((result).data).isEmpty()
        }
    }

    @Test
    fun `that error is returned from handler when error received getting repos`() {
        val repository = GithubRepository(
            givenGithubRemoteDataSource(status = RemoteDataSourceStatus.ERROR),
        )

        runBlocking {
            val result = repository.getGithubRepos("username")

            assertThat(result is Error).isTrue()
        }
    }
}