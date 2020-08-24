package com.example.githubsearcher.dagger.component

import com.example.githubsearcher.view.UsersActivity
import com.example.githubsearcher.dagger.module.GitHubViewModelMudule
import com.example.githubsearcher.dagger.scope.UsersActivityScope
import dagger.Component

@UsersActivityScope
@Component(modules = [GitHubViewModelMudule::class], dependencies = [ApplicationComponent::class])
interface UsersActivityComponent {

    fun inject(activity: UsersActivity)
}