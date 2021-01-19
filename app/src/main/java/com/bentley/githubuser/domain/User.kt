package com.bentley.githubuser.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.bentley.githubuser.domain.User.Companion.TABLE_NAME


data class SearchResult(
    val total: Int,
    val users: List<User>
)

@Entity(tableName = TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    val name: String,
    @Ignore
    var isFavorite: Boolean
) {
    constructor(name: String) : this(name, false)

    companion object {
        const val TABLE_NAME = "favoriteUsers"
    }
}
