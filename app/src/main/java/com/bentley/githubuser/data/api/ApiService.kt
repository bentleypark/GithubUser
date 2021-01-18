package com.bentley.githubuser.data.api

import com.bentley.githubuser.data.entity.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("location/search/users")
    suspend fun searchUsers(
        @Query("q") searchKeyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UserEntity
}