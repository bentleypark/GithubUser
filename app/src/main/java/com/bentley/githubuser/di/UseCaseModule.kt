package com.bentley.githubuser.di

import com.bentley.githubuser.data.GithubUserRepository
import com.bentley.githubuser.domain.GithubUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideWeatherUseCase(githubUserRepository: GithubUserRepository): GithubUserUseCase {
        return GithubUserUseCase(githubUserRepository)
    }
}