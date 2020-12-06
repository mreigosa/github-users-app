package com.mreigar.githubusers.data.injection

import com.mreigar.githubusers.data.repository.GithubRepository
import com.mreigar.githubusers.domain.repository.GithubRepositoryContract
import org.koin.dsl.module

object DataModules {

    val repositoryModule = module {
        factory<GithubRepositoryContract> { GithubRepository(get()) }
    }
}