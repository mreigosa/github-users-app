package com.mreigar.githubusers.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mreigar.githubusers.domain.executor.Success
import com.mreigar.githubusers.domain.executor.usecase.GetGithubReposUseCase
import com.mreigar.githubusers.presentation.mapper.toViewEntity
import com.mreigar.githubusers.presentation.model.GithubRepoViewEntity
import com.mreigar.githubusers.presentation.model.GithubUserViewEntity
import kotlinx.coroutines.launch

class GithubUserDetailsViewModel(
    private val getGithubReposUseCase: GetGithubReposUseCase
) : ViewModel() {

    private val _user = MutableLiveData<GithubUserViewEntity>()
    val user: LiveData<GithubUserViewEntity> = _user

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _repos = MutableLiveData<List<GithubRepoViewEntity>>()
    val repos: LiveData<List<GithubRepoViewEntity>> = _repos

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun onGithubUserFound(user: GithubUserViewEntity) {
        _user.postValue(user)
        getGithubRepos(user.username)
    }

    private fun getGithubRepos(username: String) {
        _loading.postValue(true)

        viewModelScope.launch {
            getGithubReposUseCase.run(username).let { result ->
                _loading.postValue(false)
                when (result) {
                    is Success -> _repos.postValue(result.data.map { it.toViewEntity() })
                    else -> _error.postValue(true)
                }
            }
        }
    }
}