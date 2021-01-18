package com.bentley.githubuser.data

import com.bentley.githubuser.domain.SearchResult

interface GithubUserRepository {

    suspend fun searchUsers(searchKeyword: String, page: Int): SearchResult
}