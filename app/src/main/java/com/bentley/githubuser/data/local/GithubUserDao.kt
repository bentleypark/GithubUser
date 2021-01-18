package com.bentley.githubuser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bentley.githubuser.domain.User

@Dao
interface GithubUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userHistory: User)

    @Query("SELECT * FROM favoriteUsers ORDER BY name DESC")
    suspend fun getUsers(): List<User>
}