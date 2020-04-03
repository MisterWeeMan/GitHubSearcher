package com.example.githubsearcher.dagger.component

import com.example.githubsearcher.common.network.GitHubClient
import com.example.githubsearcher.dagger.module.NetworkModule
import com.example.githubsearcher.dagger.module.RepositoryModule
import com.example.githubsearcher.repositories.GitHubRemoteRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun getGitHubClient(): GitHubClient

    fun getRemoteRepository(): GitHubRemoteRepository
}