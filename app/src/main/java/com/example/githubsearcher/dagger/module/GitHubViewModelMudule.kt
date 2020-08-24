package com.example.githubsearcher.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.example.githubsearcher.view.UsersActivity
import com.example.githubsearcher.dagger.scope.UsersActivityScope
import com.example.githubsearcher.viewmodel.GitHubViewModel
import com.example.githubsearcher.viewmodel.GitHubViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class GitHubViewModelMudule(
    private val activity: UsersActivity
) {

    @Provides
    @UsersActivityScope
    fun provideGitHubViewModel(viewModelFactory: GitHubViewModelFactory): GitHubViewModel {
        return ViewModelProvider(activity, viewModelFactory).get(GitHubViewModel::class.java)
    }
}