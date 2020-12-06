package com.mreigar.githubusers.presentation.injection

import com.mreigar.githubusers.presentation.viewmodel.GithubSearUserViewModel
import com.mreigar.githubusers.presentation.viewmodel.GithubUserDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModules {

    val presentationModules = module {
        viewModel { GithubSearUserViewModel(get()) }
        viewModel { GithubUserDetailsViewModel(get()) }
    }
}