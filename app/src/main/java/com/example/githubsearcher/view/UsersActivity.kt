package com.example.githubsearcher.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubsearcher.GitHubSearcherApplication
import com.example.githubsearcher.R
import com.example.githubsearcher.common.network.model.User
import com.example.githubsearcher.dagger.component.DaggerUsersActivityComponent
import com.example.githubsearcher.dagger.module.GitHubViewModelMudule
import com.example.githubsearcher.viewmodel.GitHubViewModel
import kotlinx.android.synthetic.main.activity_users.*
import javax.inject.Inject

class UsersActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: GitHubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        initDagger()
        initObservableData()
        getData()
    }

    private fun getData() {
        viewModel.getUserList()
    }

    private fun initObservableData() {
        viewModel.apply {

            loadingState.observe(this@UsersActivity, Observer {
                when (it) {
                    is GitHubViewModel.LoadingState.LOADING -> showLoading()
                    is GitHubViewModel.LoadingState.SUCCESS -> showList()
                    is GitHubViewModel.LoadingState.ERROR -> showError(it.message)
                }
            })

            userList.observe(this@UsersActivity, Observer {
                initRecyclerView(it)
            })
        }
    }

    private fun initRecyclerView(userList: List<User>) {
        rv_users.apply {
            adapter = UsersAdapter(userList, Glide.with(this@UsersActivity))
            layoutManager = LinearLayoutManager(this@UsersActivity)
        }
    }

    private fun showError(message: String?) {
        val errorMessage = message ?: getString(R.string.general_error_message)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(errorMessage)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> getData() }
            .setCancelable(false)
            .show()
    }

    private fun showList() {
        rv_users.visibility = View.VISIBLE
        pb_loading.visibility = View.GONE
    }

    private fun showLoading() {
        rv_users.visibility = View.GONE
        pb_loading.visibility = View.VISIBLE
    }

    private fun initDagger() {
        DaggerUsersActivityComponent.builder()
            .applicationComponent((application as GitHubSearcherApplication).applicationComponent)
            .gitHubViewModelMudule(GitHubViewModelMudule(this))
            .build()
            .inject(this)
    }
}
