package com.bentley.githubuser.di

import com.bentley.githubuser.data.GithubUserRepository
import com.bentley.githubuser.data.GithubUserRepositoryImpl
import com.bentley.githubuser.data.local.GithubUserDao
import com.bentley.githubuser.data.remote.api.ApiService
import com.bentley.githubuser.data.remote.mapper.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        userMapper: UserMapper,
        githubUserDao: GithubUserDao
    ): GithubUserRepository {
        return GithubUserRepositoryImpl(apiService, userMapper, githubUserDao)
    }

    @Singleton
    @Provides
    fun provideMapper(): UserMapper {
        return UserMapper()
    }
}