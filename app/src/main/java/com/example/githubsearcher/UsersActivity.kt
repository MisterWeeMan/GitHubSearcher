package com.example.githubsearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.githubsearcher.dagger.component.DaggerUsersActivityComponent
import com.example.githubsearcher.repositories.GitHubRemoteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: GitHubRemoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        initDagger()

        testRepository()
    }

    private fun testRepository() {
        val TAG = "Repo"

        GlobalScope.launch {
            val users = repository.downloadAllUsers()

            Log.d(TAG, "Users count: ${users.size}")

            val user = repository.downloadUser(users[0].name)

            Log.d(TAG, "First user followers: ${user.followers}")

            val repos = repository.downloadReposByUsername(user.name)

            Log.d(TAG, "Repos count for user ${user.name}: ${repos.size}")
        }
    }

    private fun initDagger() {
        DaggerUsersActivityComponent.builder()
            .applicationComponent((application as GitHubSearcherApplication).applicationComponent)
            .build()
            .inject(this)
    }
}
