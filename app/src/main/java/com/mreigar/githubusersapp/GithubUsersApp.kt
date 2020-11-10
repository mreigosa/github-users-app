package com.mreigar.githubusersapp

import android.app.Application
import com.mreigar.githubusers.data.injection.DataModules
import com.mreigar.githubusers.domain.injection.DomainModules
import com.mreigar.githubusers.network.injection.NetworkModules
import com.mreigar.githubusers.presentation.injection.PresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubUsersApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinModules()
    }

    private fun initKoinModules() {
        startKoin {
            androidContext(this@GithubUsersApp)
            modules(
                NetworkModules.networkModule,
                DataModules.repositoryModule,
                DomainModules.domainModule,
                PresentationModules.presentationModules
            )
        }
    }
}