package com.bentley.githubuser.ui.api

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bentley.githubuser.domain.GithubUserUseCase
import com.bentley.githubuser.domain.User
import com.bentley.githubuser.domain.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class ApiFragmentViewModel @ViewModelInject
constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {

//    val text: LiveData<String> = Transformations.map(_index) {
//        "Hello world from section: $it"
//    }

    val list = mutableListOf(
        User("테스트", ""), User("박중길", ""), User("&^*", ""),
        User(
            "test",
            ""
        )
    )

    private val _userList = MutableLiveData<DataState<List<User>>>()
    val userList: LiveData<DataState<List<User>>> get() = _userList

    fun searchUsers() {
        viewModelScope.launch {
            githubUserUseCase.searchUsers("bent")
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                    _userList.value = dataState
                }
                .launchIn(viewModelScope)
        }

    }

    fun insert() {
        viewModelScope.launch {
            githubUserUseCase.insert(User("test", ""))
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                }
                .launchIn(viewModelScope)
        }
    }
}