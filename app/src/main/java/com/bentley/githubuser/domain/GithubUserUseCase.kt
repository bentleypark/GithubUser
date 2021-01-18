package com.bentley.githubuser.domain

import com.bentley.githubuser.data.GithubUserRepository
import com.bentley.githubuser.domain.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GithubUserUseCase @Inject constructor(private val githubUserRepository: GithubUserRepository) {

    suspend fun searchUsers(searchKeyword: String, page: Int = 1): Flow<DataState<List<User>>> =
        flow {
            emit(DataState.Loading)
            delay(1000)

            try {
                val result = githubUserRepository.searchUsers(searchKeyword, page)
                if (result.total != 0) {
                    emit(DataState.Success(result.users))
                } else {
                    val e = Exception("검색 결과가 없습니다.")
                    emit(DataState.Error(e))
                }

            } catch (e: Exception) {
                emit(DataState.Error(e))
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