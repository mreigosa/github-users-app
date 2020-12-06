package com.mreigar.githubusers.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mreigar.githubusers.domain.executor.Error
import com.mreigar.githubusers.domain.executor.Success
import com.mreigar.githubusers.domain.executor.usecase.GetGithubReposUseCase
import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.model.GithubUser
import com.mreigar.githubusers.presentation.MainCoroutineRule
import com.mreigar.githubusers.presentation.mapper.toViewEntity
import com.mreigar.githubusers.presentation.model.GithubRepoViewEntity
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
class GithubUserDetailsViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val userObserver = mock<Observer<GithubUserViewEntity>>()
    private val reposObserver = mock<Observer<List<GithubRepoViewEntity>>>()
    private val errorObserver = mock<Observer<Boolean>>()

    private val loadingCaptor: KArgumentCaptor<Boolean> = argumentCaptor()
    private val githubReposCaptor: KArgumentCaptor<List<GithubRepoViewEntity>> = argumentCaptor()

    private val getGithubReposUseCase = mock<GetGithubReposUseCase>()

    private lateinit var viewModel: GithubUserDetailsViewModel

    @Before
    fun before() {
        startKoin {}

        viewModel = GithubUserDetailsViewModel(getGithubReposUseCase)
    }

    @Test
    fun `given view model, when screen is opened, user is shown`() = runBlockingTest {
        val githubUser = GithubUserViewEntity("mreigosa", "www.avatar.url", "name", "Android dev", "Spain")
        val githubRepo = GithubRepo("github-users-app", "Kotlin")

        given(getGithubReposUseCase.run("username")).willReturn(Success(listOf(githubRepo)))

        viewModel.user.observeForever(userObserver)

        viewModel.onGithubUserFound(githubUser)

        verify(userObserver).onChanged(githubUser)
    }

    @Test
    fun `given view model, when repos are searched, loading is is shown`() = runBlockingTest {
        val githubUser = GithubUserViewEntity("mreigosa", "www.avatar.url", "name", "Android dev", "Spain")
        val githubRepo = GithubRepo("github-users-app", "Kotlin")

        given(getGithubReposUseCase.run("username")).willReturn(Success(listOf(githubRepo)))

        viewModel.loading.observeForever(loadingObserver)

        viewModel.onGithubUserFound(githubUser)

        verify(loadingObserver).onChanged(true)
    }

    @Test
    fun `given view model, when use case returns success result, repos are updated`() = runBlockingTest {
        val githubUser = GithubUserViewEntity("mreigosa", "www.avatar.url", "name", "Android dev", "Spain")
        val githubRepo = GithubRepo("github-users-app", "Kotlin")

        given(getGithubReposUseCase.run("mreigosa")).willReturn(Success(listOf(githubRepo)))

        viewModel.repos.observeForever(reposObserver)

        viewModel.onGithubUserFound(githubUser)

        verify(reposObserver).onChanged(githubReposCaptor.capture())
        Assertions.assertThat(githubReposCaptor.allValues[0][0]).isEqualTo(githubRepo.toViewEntity())
    }

    @Test
    fun `given view model, when use cases returns success result, loading is hidden`() = runBlockingTest {
        val githubUser = GithubUserViewEntity("mreigosa", "www.avatar.url", "name", "Android dev", "Spain")

        given(getGithubReposUseCase.run("username")).willReturn(Success(listOf()))

        viewModel.loading.observeForever(loadingObserver)

        viewModel.onGithubUserFound(githubUser)

        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        Assertions.assertThat(loadingCaptor.allValues[0]).isTrue()
        Assertions.assertThat(loadingCaptor.allValues[1]).isFalse()
    }

    @Test
    fun `given view model, when use case returns error result, user is updated`() = runBlockingTest {
        val githubUser = GithubUserViewEntity("id.name", "www.avatar.url", "name", "Android dev", "Spain")

        given(getGithubReposUseCase.run("username")).willReturn(Error())

        viewModel.error.observeForever(errorObserver)

        viewModel.onGithubUserFound(githubUser)

        verify(errorObserver).onChanged(true)
    }
}