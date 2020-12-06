package com.mreigar.githubusers.domain.repository

import com.mreigar.githubusers.domain.executor.Result
import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.model.GithubUser

interface GithubRepositoryContract {
    suspend fun getUser(username: String): Result<GithubUser>
    suspend fun getGithubRepos(username: String): Result<List<GithubRepo>>
}