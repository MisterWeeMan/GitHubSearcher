package com.example.githubsearcher.repositories

import com.example.githubsearcher.common.network.model.Repo
import com.example.githubsearcher.common.network.model.User

interface GitHubRemoteRepository {

    suspend fun downloadAllUsers(): List<User>

    suspend fun downloadUser(username: String): User

    suspend fun downloadReposByUsername(username: String): List<Repo>
}