package com.mreigar.githubusers.domain.mock

import com.mreigar.githubusers.domain.executor.Error
import com.mreigar.githubusers.domain.executor.Result
import com.mreigar.githubusers.domain.executor.Success
import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.model.GithubUser
import com.mreigar.githubusers.domain.repository.GithubRepositoryContract

enum class RepositoryStatus {
    SUCCESS, ERROR
}

object DomainInstrument {

    fun givenGithubRepository(
        status: RepositoryStatus = RepositoryStatus.SUCCESS
    ) = object : GithubRepositoryContract {

        override suspend fun getUser(username: String): Result<GithubUser> = when (status) {
            RepositoryStatus.SUCCESS -> Success(GithubUser("id.name", "www.avatar.url", "name", "Android dev", "Spain"))
            RepositoryStatus.ERROR -> Error()
        }

        override suspend fun getGithubRepos(username: String): Result<List<GithubRepo>> = when (status) {
            RepositoryStatus.SUCCESS -> Success(listOf(GithubRepo("github-users-app", "Kotlin")))
            RepositoryStatus.ERROR -> Error()
        }
    }

//    fun givenAGenericSuccessResultUseCase() = object : UseCase<Unit, String>() {
//        override suspend fun run(): Result<String> = Success("Awesome Result")
//    }
//
//    class TestContextProvider : DispatcherProvider {
//        override val main: CoroutineDispatcher = Dispatchers.Unconfined
//        override val background: CoroutineDispatcher = Dispatchers.Unconfined
//    }

}