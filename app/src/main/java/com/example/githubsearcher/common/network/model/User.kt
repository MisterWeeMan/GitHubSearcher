package com.example.githubsearcher.common.network.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("email") val email: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("created_at") val joinDate: String?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
    @SerializedName("bio") val bio: String?
)