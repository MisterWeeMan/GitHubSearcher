package com.example.githubsearcher

import android.app.Application
import com.example.githubsearcher.dagger.component.ApplicationComponent
import com.example.githubsearcher.dagger.component.DaggerApplicationComponent

class GitHubSearcherApplication: Application() {

    val applicationComponent: ApplicationComponent by lazy { DaggerApplicationComponent.create() }
}