package com.mreigar.githubusers.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mreigar.githubusers.domain.executor.Success
import com.mreigar.githubusers.domain.executor.usecase.GetGithubUserUseCase
import com.mreigar.githubusers.domain.model.GithubUser
import kotlinx.coroutines.launch

class GithubSearUserViewModel(
    private val getUserUseCase: GetGithubUserUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _user = MutableLiveData<GithubUser>()
    val user: LiveData<GithubUser> = _user

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun onSearchClicked(username: String) {
        if (username.isNotEmpty()) {
            _loading.postValue(true)

            viewModelScope.launch {
                getUserUseCase.run(username).let { result ->
                    _loading.postValue(false)
                    when (result) {
                        is Success -> _user.postValue(result.data)
                        else -> _error.postValue(true)
                    }
                }
            }
        }
    }
}