package com.bentley.githubuser.ui.api

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bentley.githubuser.domain.GithubUserUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class ApiFragmentViewModel @ViewModelInject
constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {

//    val text: LiveData<String> = Transformations.map(_index) {
//        "Hello world from section: $it"
//    }

    fun searchUsers() {
        viewModelScope.launch {
            githubUserUseCase.searchUsers("test")
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                }
                .launchIn(viewModelScope)
        }
    }
}