package com.mreigar.githubusers.domain

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val background: CoroutineDispatcher
}