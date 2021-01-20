package com.bentley.githubuser.data

import com.bentley.githubuser.data.local.GithubUserDao
import com.bentley.githubuser.data.remote.api.ApiService
import com.bentley.githubuser.data.remote.mapper.UserMapper
import com.bentley.githubuser.domain.SearchResult
import com.bentley.githubuser.domain.User
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userMapper: UserMapper,
    private val githubUserDao: GithubUserDao
) :
    GithubUserRepository {
    override suspend fun searchUsers(query: String, page: Int): SearchResult {

        val result = apiService.searchUsers(query, page, PER_PAGE)
        return SearchResult(result.total, userMapper.mapFromEntity(result.items))
    }

    override suspend fun insert(user: User) {
        return githubUserDao.insert(user)
    }

    override suspend fun delete(user: User) {
        return githubUserDao.delete(user)
    }

    override suspend fun getUsers(): List<User> {
        return githubUserDao.getUsers()
    }

    override suspend fun search(query: String): List<User> {
        return githubUserDao.searchUsers(query)
    }

    companion object {
        const val PER_PAGE = 100
    }
}