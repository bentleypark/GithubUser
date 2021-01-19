package com.bentley.githubuser.ui.api

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bentley.githubuser.domain.GithubUserUseCase
import com.bentley.githubuser.domain.User
import com.bentley.githubuser.domain.state.DataState
import kotlinx.coroutines.delay
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
        User("테스트"), User("박중길"), User("&^*"),
        User("test")
    )

    private val _userList = MutableLiveData<DataState<List<User>>>()
    val userList: LiveData<DataState<List<User>>> get() = _userList

    private val _nextUserList = MutableLiveData<DataState<List<User>>>()
    val nextUserList: LiveData<DataState<List<User>>> get() = _nextUserList

    private var currentPage = 1
    private var currentQuery = ""

    fun searchUsers(query: String) {
        viewModelScope.launch {
            checkNewQuery(query)
            delay(1000)
            githubUserUseCase.searchUsers(query, currentPage)
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                    _userList.value = dataState
                }
                .launchIn(viewModelScope)
        }

    }

    private fun checkNewQuery(newQuery: String) {
        if (currentQuery.isEmpty()) {
            currentQuery = newQuery
        }

        if (currentQuery != newQuery) {
            currentQuery = newQuery
            currentPage = 1
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            currentPage += 1
            githubUserUseCase.searchUsers(currentQuery, currentPage)
                .onEach { dataState ->
                    _nextUserList.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun insert() {
        viewModelScope.launch {
            githubUserUseCase.insert(User("test"))
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                }
                .launchIn(viewModelScope)
        }
    }
}