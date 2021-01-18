package com.bentley.githubuser.data

import com.bentley.githubuser.data.api.ApiService
import com.bentley.githubuser.data.mapper.UserMapper
import com.bentley.githubuser.domain.SearchResult
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userMapper: UserMapper
) :
    GithubUserRepository {
    override suspend fun searchUsers(searchKeyword: String, page: Int): SearchResult {

        val result = apiService.searchUsers(searchKeyword, page, PER_PAGE)
        return SearchResult(result.total, userMapper.mapFromEntity(result.items))
    }

    companion object {
        const val PER_PAGE = 100
    }
}