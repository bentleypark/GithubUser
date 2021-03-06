package com.bentley.githubuser.data.remote.api

import com.bentley.githubuser.data.remote.entity.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") searchKeyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UserEntity
}