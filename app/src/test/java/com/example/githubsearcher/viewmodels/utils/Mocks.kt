package com.example.githubsearcher.viewmodels.utils

import com.example.githubsearcher.common.network.model.Repo
import com.example.githubsearcher.common.network.model.User

val userMock = User(
    name = "octocat",
    avatarUrl = "https://github.com/images/error/octocat_happy.gif",
    email = "octocat@github.com",
    location = "San Francisco",
    joinDate = "2008-01-14T04:33:35Z",
    followers = 20,
    following = 12,
    bio = null
)

val repoMock = Repo(
    name = "Hello-World",
    repoUrl = "https://github.com/octocat/Hello-World",
    forks = 9,
    stars = 80
)