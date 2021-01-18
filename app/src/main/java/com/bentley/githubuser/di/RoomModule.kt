package com.bentley.githubuser.di

import android.content.Context
import androidx.room.Room
import com.bentley.githubuser.data.local.GithubUserDao
import com.bentley.githubuser.data.local.GithubUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideConsumableDb(@ApplicationContext context: Context): GithubUserDatabase {
        return Room
            .databaseBuilder(
                context,
                GithubUserDatabase::class.java,
                GithubUserDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDAO(database: GithubUserDatabase): GithubUserDao {
        return database.githubUserDao()
    }
}