package com.example.githubsearcher.dagger.component

import com.example.githubsearcher.common.network.GitHubClient
import com.example.githubsearcher.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun getGitHubClient(): GitHubClient
}