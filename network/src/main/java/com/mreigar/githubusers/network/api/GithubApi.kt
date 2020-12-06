package com.mreigar.githubusers.network.api

import com.mreigar.githubusers.network.model.GithubRepoRemoteEntity
import com.mreigar.githubusers.network.model.GithubUserRemoteEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    @GET("users/{username}")
    suspend fun getUser(@Path("username") user: String): GithubUserRemoteEntity

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") user: String): List<GithubRepoRemoteEntity>
}