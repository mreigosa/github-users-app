package com.mreigar.githubusers.domain.executor.usecase

import com.mreigar.githubusers.domain.executor.Result
import com.mreigar.githubusers.domain.model.GithubUser
import com.mreigar.githubusers.domain.repository.GithubRepositoryContract

class GetGithubUserUseCase(
    private val githubRepository: GithubRepositoryContract
) {

    suspend fun run(useCaseParams: String): Result<GithubUser> {
        return githubRepository.getUser(useCaseParams)
    }
}