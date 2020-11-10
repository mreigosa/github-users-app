package com.mreigar.githubusers.domain.injection

import com.mreigar.githubusers.domain.executor.usecase.GetGithubReposUseCase
import com.mreigar.githubusers.domain.executor.usecase.GetGithubUserUseCase
import org.koin.dsl.module

object DomainModules {

    val domainModule = module {
        factory { GetGithubUserUseCase(get()) }
        factory { GetGithubReposUseCase(get()) }
    }
}