package com.example.githubsearcher.repositories

import com.example.githubsearcher.common.network.GitHubClient
import com.example.githubsearcher.common.network.model.Repo
import com.example.githubsearcher.common.network.model.User
import javax.inject.Inject

class GitHubRetrofitRepository @Inject constructor(
    private val gitHubClient: GitHubClient
): GitHubRemoteRepository {

    override suspend fun downloadAllUsers(): List<User> {
        return gitHubClient
            .getAllUsers()
            .map {
                val reposByUser = gitHubClient.getReposByUser(it.name)
                it.copy(
                    reposCount = reposByUser.size
                )
            }
    }

    override suspend fun downloadUser(username: String): User {
        return gitHubClient.getUser(username)
    }

    override suspend fun downloadReposByUsername(username: String): List<Repo> {
        return gitHubClient.getReposByUser(username)
    }
}