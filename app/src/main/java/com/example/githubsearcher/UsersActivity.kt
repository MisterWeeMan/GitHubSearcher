package com.example.githubsearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.githubsearcher.dagger.component.DaggerUsersActivityComponent
import com.example.githubsearcher.dagger.module.GitHubViewModelMudule
import com.example.githubsearcher.repositories.GitHubRemoteRepository
import com.example.githubsearcher.viewmodel.GitHubViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: GitHubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        initDagger()

        testRepository()

        viewModel.getUserList()
    }

    private fun testRepository() {
        val TAG = "ViewModel"

        viewModel.apply {

            loadingState.observe(this@UsersActivity, Observer {
                when (it) {
                    is GitHubViewModel.LoadingState.LOADING -> Log.d(TAG, "Loading...")
                    is GitHubViewModel.LoadingState.SUCCESS -> Log.d(TAG, "Success!")
                    is GitHubViewModel.LoadingState.ERROR -> Log.d(TAG, "Error: ${it.message}")
                }
            })

            userList.observe(this@UsersActivity, Observer {
                Log.d(TAG, "Users count: ${it.size}")

                val username = it[0].name
                getUser(username)
                getReposByUser(username)
            })

            user.observe(this@UsersActivity, Observer {
                Log.d(TAG, "First user followers: ${it.followers}")
            })

            repoListByUser.observe(this@UsersActivity, Observer {
                Log.d(TAG, "Repos count: ${it.size}")
            })
        }
    }

    private fun initDagger() {
        DaggerUsersActivityComponent.builder()
            .applicationComponent((application as GitHubSearcherApplication).applicationComponent)
            .gitHubViewModelMudule(GitHubViewModelMudule(this))
            .build()
            .inject(this)
    }
}
