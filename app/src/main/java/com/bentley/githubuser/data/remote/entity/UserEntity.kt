package com.bentley.githubuser.data.remote.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("total_count")
    val total: Int,
    @SerializedName("items")
    val items: List<UserInfoEntity>,
)

data class UserInfoEntity(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val profileUrl: String,
)
