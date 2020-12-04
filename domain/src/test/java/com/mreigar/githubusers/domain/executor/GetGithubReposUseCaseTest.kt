package com.mreigar.githubusers.domain.executor

import com.mreigar.githubusers.domain.executor.usecase.GetGithubReposUseCase
import com.mreigar.githubusers.domain.mock.DomainInstrument.givenGithubRepository
import com.mreigar.githubusers.domain.mock.RepositoryStatus
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetGithubReposUseCaseTest {

    @Test
    fun `that use case returns success result`() {
        val useCase = GetGithubReposUseCase(givenGithubRepository())

        runBlocking {
            val result = useCase.run("username")

            assertThat(result).isNotNull()
            assertThat(result is Success).isTrue()
        }
    }

    @Test
    fun `that use case returns error result`() {
        val useCase = GetGithubReposUseCase(givenGithubRepository(status = RepositoryStatus.ERROR))

        runBlocking {
            val result = useCase.run("username")

            assertThat(result).isNotNull()
            assertThat(result is Error).isTrue()
        }
    }
}