package com.example.githubsearcher.common.network

import com.example.githubsearcher.common.GET_ALL_USERS_ENDPOINT
import com.example.githubsearcher.common.GET_USER_ENDPOINT
import com.example.githubsearcher.common.GET_USER_REPOS_ENDPOINT
import com.example.githubsearcher.common.USERNAME
import com.example.githubsearcher.common.network.model.Repo
import com.example.githubsearcher.common.network.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubClient {

    @GET(GET_ALL_USERS_ENDPOINT)
    suspend fun getAllUsers(): List<User>

    @GET(GET_USER_ENDPOINT)
    suspend fun getUser(@Path(USERNAME) username: String): User

    @GET(GET_USER_REPOS_ENDPOINT)
    suspend fun getReposByUser(@Path(USERNAME) username: String): List<Repo>
}