package com.bentley.githubuser.ui.local

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bentley.githubuser.domain.GithubUserUseCase
import com.bentley.githubuser.domain.User
import com.bentley.githubuser.domain.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class LocalViewModel @ViewModelInject
constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<User>>>()
    val dataState: LiveData<DataState<List<User>>> get() = _dataState

    fun delete(user: User) {
        viewModelScope.launch {
            githubUserUseCase.delete(user)
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                }
                .launchIn(viewModelScope)
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            githubUserUseCase.search(query)
                .onEach { dataState ->
                    _dataState.postValue(dataState)
                }
                .launchIn(viewModelScope)
        }
    }
}