package com.bentley.githubuser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bentley.githubuser.domain.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao

    companion object {
        const val DATABASE_NAME: String = "user_db"
    }
}