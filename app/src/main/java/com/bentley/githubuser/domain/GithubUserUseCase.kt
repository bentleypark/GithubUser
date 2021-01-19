package com.bentley.githubuser.domain

import com.bentley.githubuser.data.GithubUserRepository
import com.bentley.githubuser.data.local.GithubUserDao
import com.bentley.githubuser.domain.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import java.lang.Exception
import javax.inject.Inject

class GithubUserUseCase @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) {

    suspend fun searchUsers(searchKeyword: String, page: Int = 1): Flow<DataState<List<User>>> =
        flow {
            emit(DataState.Loading)
            delay(1000)

            try {
                val searchResult = githubUserRepository.searchUsers(searchKeyword, page)
                val getResult = githubUserRepository.getUsers().map { it.name }

                searchResult.users.forEach { user ->
                    if (getResult.contains(user.name)) {
                        user.isFavorite = true
                    }
                }

                if (searchResult.total != 0) {
                    emit(DataState.Success(searchResult.users))
                } else {
                    val e = Exception("검색 결과가 없습니다.")
                    emit(DataState.Error(e))
                }

            } catch (e: Exception) {
                emit(DataState.Error(Exception("오류가 발생했습니다. 잠시 후에 다시 한번 시도해주세요. $e")))
            }
        }

    suspend fun insert(user: User): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        delay(1000)

        try {
            githubUserRepository.insert(user)
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun delete(user: User): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        delay(1000)

        try {
            githubUserRepository.delete(user)
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getUsers(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Loading)
        delay(1000)

        try {
            val result = githubUserRepository.getUsers()
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}