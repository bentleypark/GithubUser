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

    // 최초 검색 결과 리스트  정보
    private val _userList = MutableLiveData<DataState<List<User>>>()
    val userList: LiveData<DataState<List<User>>> get() = _userList
    // 추가 페이지 리스트 정보
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
            currentPage = FIRST_PAGE
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

    fun insert(item: User) {
        viewModelScope.launch {
            githubUserUseCase.insert(item)
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                }
                .launchIn(viewModelScope)
        }
    }

    fun delete(item: User) {
        viewModelScope.launch {
            githubUserUseCase.delete(item)
                .onEach { dataState ->
                    Timber.d(dataState.toString())
                }
                .launchIn(viewModelScope)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}