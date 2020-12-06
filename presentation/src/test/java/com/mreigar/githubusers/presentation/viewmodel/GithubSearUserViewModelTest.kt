package com.mreigar.githubusers.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mreigar.githubusers.domain.executor.Error
import com.mreigar.githubusers.domain.executor.Success
import com.mreigar.githubusers.domain.executor.usecase.GetGithubUserUseCase
import com.mreigar.githubusers.domain.model.GithubUser
import com.mreigar.githubusers.presentation.MainCoroutineRule
import com.mreigar.githubusers.presentation.mapper.toViewEntity
import com.mreigar.githubusers.presentation.model.GithubUserViewEntity
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
class GithubSearUserViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val dataObserver = mock<Observer<GithubUserViewEntity>>()
    private val errorObserver = mock<Observer<Boolean>>()

    private val loadingCaptor: KArgumentCaptor<Boolean> = argumentCaptor()

    private val getGithubUserUseCase = mock<GetGithubUserUseCase>()

    private lateinit var viewModel: GithubSearUserViewModel

    @Before
    fun before() {
        startKoin {}

        viewModel = GithubSearUserViewModel(getGithubUserUseCase)
    }

    @Test
    fun `given view model, when search button clicked, loading is shown`() = runBlockingTest {
        val githubUser = GithubUser("mreigosa", "www.avatar.url", "name", "Android dev", "Spain")

        given(getGithubUserUseCase.run("mreigosa")).willReturn(Success(githubUser))

        viewModel.loading.observeForever(loadingObserver)

        viewModel.onSearchClicked("mreigosa")

        verify(loadingObserver).onChanged(true)
    }

    @Test
    fun `given view model, when use case returns success result, user is updated`() = runBlockingTest {
        val githubUser = GithubUser("mreigosa", "www.avatar.url", "name", "Android dev", "Spain")

        given(getGithubUserUseCase.run("mreigosa")).willReturn(Success(githubUser))

        viewModel.user.observeForever(dataObserver)

        viewModel.onSearchClicked("mreigosa")

        verify(dataObserver).onChanged(githubUser.toViewEntity())
    }

    @Test
    fun `given view model, when use cases returns success result, loading is hidden`() = runBlockingTest {
        val githubUser = GithubUser("mreigosa", "www.avatar.url", "name", "Android dev", "Spain")

        given(getGithubUserUseCase.run("mreigosa")).willReturn(Success(githubUser))

        viewModel.loading.observeForever(loadingObserver)

        viewModel.onSearchClicked("mreigosa")

        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        Assertions.assertThat(loadingCaptor.allValues[0]).isTrue()
        Assertions.assertThat(loadingCaptor.allValues[1]).isFalse()
    }

    @Test
    fun `given view model, when use case returns error result, user is updated`() = runBlockingTest {
        given(getGithubUserUseCase.run("mreigosa")).willReturn(Error())

        viewModel.error.observeForever(errorObserver)

        viewModel.onSearchClicked("mreigosa")

        verify(errorObserver).onChanged(true)
    }

}