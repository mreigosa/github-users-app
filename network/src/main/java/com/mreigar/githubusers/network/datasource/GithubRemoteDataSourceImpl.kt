package com.mreigar.githubusers.network.datasource

import com.mreigar.githubusers.data.datasource.GithubRemoteDataSourceContract
import com.mreigar.githubusers.data.model.GithubRepoEntity
import com.mreigar.githubusers.data.model.GithubUserEntity
import com.mreigar.githubusers.network.api.GithubApi
import com.mreigar.githubusers.network.exception.NetworkException
import com.mreigar.githubusers.network.mapper.toDataEntity

class GithubRemoteDataSourceImpl(
    private val api: GithubApi
) : GithubRemoteDataSourceContract {

    override suspend fun getUser(user: String): GithubUserEntity = try {
        api.getUser(user).toDataEntity()
    } catch (e: Exception) {
        throw NetworkException(e)
    }

    override suspend fun getUserRepos(user: String): List<GithubRepoEntity> = try {
        api.getUserRepos(user).map { it.toDataEntity() }
    } catch (e: Exception) {
        throw NetworkException(e)
    }

}