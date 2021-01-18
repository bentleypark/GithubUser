package com.bentley.githubuser.domain


data class SearchResult(
    val total : Int,
    val users: List<User>
)

data class User(
    val name: String,
    val profileUrl: String,
    val isFavorite: Boolean
) {
    constructor(name: String, profileUrl: String) : this(name, profileUrl, false)
}
