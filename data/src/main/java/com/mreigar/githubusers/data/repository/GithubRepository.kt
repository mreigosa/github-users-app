package com.mreigar.githubusers.data.repository

import com.mreigar.githubusers.data.datasource.GithubRemoteDataSourceContract
import com.mreigar.githubusers.data.mapper.toDomainEntity
import com.mreigar.githubusers.domain.executor.Error
import com.mreigar.githubusers.domain.executor.Result
import com.mreigar.githubusers.domain.executor.Success
import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.model.GithubUser
import com.mreigar.githubusers.domain.repository.GithubRepositoryContract

class GithubRepository(
    private val remoteDataSource: GithubRemoteDataSourceContract
) : GithubRepositoryContract {

    override suspend fun getUser(username: String): Result<GithubUser> = try {
        val remoteResponse = remoteDataSource.getUser(username)
        Success(remoteResponse.toDomainEntity())
    } catch (e: Exception) {
        Error(e)
    }

    override suspend fun getGithubRepos(username: String): Result<List<GithubRepo>> = try {
        val remoteResponse = remoteDataSource.getUserRepos(username)
        Success(remoteResponse.map { it.toDomainEntity() })
    } catch (e: Exception) {
        Error(e)
    }
}