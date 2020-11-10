package com.mreigar.githubusers.data.datasource

import com.mreigar.githubusers.data.model.GithubRepoEntity
import com.mreigar.githubusers.data.model.GithubUserEntity

interface GithubRemoteDataSourceContract {
    suspend fun getUser(user: String): GithubUserEntity
    suspend fun getUserRepos(user: String): List<GithubRepoEntity>
}