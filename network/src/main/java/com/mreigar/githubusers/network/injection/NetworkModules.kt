package com.mreigar.githubusers.network.injection

import com.mreigar.githubusers.data.datasource.GithubRemoteDataSourceContract
import com.mreigar.githubusers.network.api.GithubApi
import com.mreigar.githubusers.network.api.NetworkApi
import com.mreigar.githubusers.network.datasource.GithubRemoteDataSourceImpl
import org.koin.dsl.module

object NetworkModules {

    val networkModule = module {
        single { NetworkApi().provideApi(GithubApi.BASE_URL, GithubApi::class.java) }

        factory<GithubRemoteDataSourceContract> { GithubRemoteDataSourceImpl(get()) }
    }
}