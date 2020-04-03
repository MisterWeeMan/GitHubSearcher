package com.example.githubsearcher.dagger.component

import com.example.githubsearcher.UsersActivity
import com.example.githubsearcher.dagger.scope.UsersActivityScope
import dagger.Component

@UsersActivityScope
@Component(dependencies = [ApplicationComponent::class])
interface UsersActivityComponent {

    fun inject(activity: UsersActivity)
}