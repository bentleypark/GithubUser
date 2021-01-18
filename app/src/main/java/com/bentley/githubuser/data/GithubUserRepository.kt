package com.bentley.githubuser.data

import com.bentley.githubuser.domain.SearchResult
import com.bentley.githubuser.domain.User

interface GithubUserRepository {

    suspend fun searchUsers(searchKeyword: String, page: Int): SearchResult

    suspend fun insert(user: User)

    suspend fun getUsers(): List<User>
}