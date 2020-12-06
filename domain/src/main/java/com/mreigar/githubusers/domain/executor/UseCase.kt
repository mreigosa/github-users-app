package com.mreigar.githubusers.domain.executor

import com.mreigar.githubusers.domain.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<P : Any, T>(
    private val dispatcherProvider: DispatcherProvider = DispatcherProviderImpl()
) {

    abstract suspend fun run(useCaseParams: P): Result<T>

    open fun execute(
        scope: CoroutineScope,
        useCaseParams: P,
        callback: ((Result<T>) -> Unit)?
    ) {
        scope.launch(dispatcherProvider.main) {
            try {
                val result = withContext(dispatcherProvider.background) { run(useCaseParams) }
                callback?.invoke(result)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }
}