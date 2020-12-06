package com.mreigar.githubusers.domain.executor.usecase

import com.mreigar.githubusers.domain.executor.Result
import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.repository.GithubRepositoryContract

class GetGithubReposUseCase(
    private val githubRepository: GithubRepositoryContract
) {

    suspend fun run(useCaseParams: String): Result<List<GithubRepo>> {
        return githubRepository.getGithubRepos(useCaseParams)
    }
}