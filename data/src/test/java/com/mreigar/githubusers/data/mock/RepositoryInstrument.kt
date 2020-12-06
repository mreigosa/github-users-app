package com.mreigar.githubusers.data.mock

import com.mreigar.githubusers.data.datasource.GithubRemoteDataSourceContract
import com.mreigar.githubusers.data.model.GithubRepoEntity
import com.mreigar.githubusers.data.model.GithubUserEntity

enum class RemoteDataSourceStatus {
    SUCCESS, ERROR
}

object RepositoryInstrument {

    fun givenGithubRemoteDataSource(
        status: RemoteDataSourceStatus = RemoteDataSourceStatus.SUCCESS,
        repos: List<GithubRepoEntity>? = null
    ) = object : GithubRemoteDataSourceContract {

        override suspend fun getUser(user: String): GithubUserEntity = when (status) {
            RemoteDataSourceStatus.SUCCESS -> GithubUserEntity("test-username", "avatar-url.com", "User Name", "Android dev", "Spain")
            RemoteDataSourceStatus.ERROR -> throw Exception()
        }

        override suspend fun getUserRepos(user: String): List<GithubRepoEntity> = when (status) {
            RemoteDataSourceStatus.SUCCESS -> repos ?: listOf(GithubRepoEntity("Repo name", "Kotlin"))
            RemoteDataSourceStatus.ERROR -> throw Exception()
        }
    }

}