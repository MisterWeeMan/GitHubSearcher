package com.example.githubsearcher.dagger.module

import com.example.githubsearcher.repositories.GitHubRemoteRepository
import com.example.githubsearcher.repositories.GitHubRetrofitRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRetrofitRepository(retrofitRepository: GitHubRetrofitRepository): GitHubRemoteRepository
}