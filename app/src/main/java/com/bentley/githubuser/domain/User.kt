package com.bentley.githubuser.domain

import androidx.room.*

data class SearchResult(
    val total: Int,
    val users: List<User>
)

@Entity(tableName = "favoriteUsers")
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @Ignore
    var isFavorite: Boolean
) {
    constructor(id: Int, name: String) : this(id, name, false)
}



