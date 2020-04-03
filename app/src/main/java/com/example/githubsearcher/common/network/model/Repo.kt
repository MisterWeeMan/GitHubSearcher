package com.example.githubsearcher.common.network.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val repoUrl: String,
    @SerializedName("forks") val forks: Int,
    @SerializedName("stargazers_count") val stars: Int
)