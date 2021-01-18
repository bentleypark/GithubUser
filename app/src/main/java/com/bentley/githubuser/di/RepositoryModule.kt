package com.bentley.githubuser.di

import com.bentley.githubuser.data.GithubUserRepository
import com.bentley.githubuser.data.GithubUserRepositoryImpl
import com.bentley.githubuser.data.api.ApiService
import com.bentley.githubuser.data.mapper.UserMapper
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
    ): GithubUserRepository {
        return GithubUserRepositoryImpl(apiService, userMapper)
    }

    @Singleton
    @Provides
    fun provideMapper(): UserMapper {
        return UserMapper()
    }
}