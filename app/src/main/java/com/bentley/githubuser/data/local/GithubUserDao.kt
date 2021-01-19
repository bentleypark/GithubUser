package com.bentley.githubuser.data.local

import androidx.room.*
import com.bentley.githubuser.domain.User

@Dao
interface GithubUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM favoriteUsers ORDER BY name DESC")
    suspend fun getUsers(): List<User>
}