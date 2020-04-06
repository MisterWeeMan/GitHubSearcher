package com.example.githubsearcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearcher.repositories.GitHubRemoteRepository
import javax.inject.Inject

class GitHubViewModelFactory @Inject constructor(
    private val repository: GitHubRemoteRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GitHubViewModel(repository) as T
    }
}