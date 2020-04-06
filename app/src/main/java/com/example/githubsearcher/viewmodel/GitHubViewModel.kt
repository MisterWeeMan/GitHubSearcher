package com.example.githubsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearcher.common.network.model.Repo
import com.example.githubsearcher.common.network.model.User
import com.example.githubsearcher.repositories.GitHubRemoteRepository
import kotlinx.coroutines.launch

class GitHubViewModel(
    private val repository: GitHubRemoteRepository
): ViewModel() {

    sealed class LoadingState {
        object LOADING: LoadingState()
        object SUCCESS: LoadingState()
        data class ERROR(val message: String?): LoadingState()
    }

    val loadingState: LiveData<LoadingState>
        get() = mLoadingState
    private val mLoadingState = MutableLiveData<LoadingState>()

    val userList: LiveData<List<User>>
        get() = mUserList
    private val mUserList = MutableLiveData<List<User>>()

    val user: LiveData<User>
        get() = mUser
    private val mUser = MutableLiveData<User>()

    val repoListByUser: LiveData<List<Repo>>
        get() = mRepoListByUser
    private val mRepoListByUser = MutableLiveData<List<Repo>>()

    fun getUserList() {
        mLoadingState.value = LoadingState.LOADING

        viewModelScope.launch {
            try {
                val users = repository.downloadAllUsers()

                mUserList.value = users
                mLoadingState.value = LoadingState.SUCCESS

            } catch (exc: Exception) {
                mLoadingState.value = LoadingState.ERROR(exc.message)
            }
        }
    }

    fun getUser(username: String) {
        mLoadingState.value = LoadingState.LOADING

        viewModelScope.launch {
            try {
                val user = repository.downloadUser(username)

                mUser.value = user
                mLoadingState.value = LoadingState.SUCCESS
            } catch (exc: Exception) {
                mLoadingState.value = LoadingState.ERROR(exc.message)
            }
        }
    }

    fun getReposByUser(username: String) {
        mLoadingState.value = LoadingState.LOADING

        viewModelScope.launch {
            try {
                val repos = repository.downloadReposByUsername(username)

                mRepoListByUser.value = repos
                mLoadingState.value = LoadingState.SUCCESS
            } catch (exc: Exception) {
                mLoadingState.value = LoadingState.ERROR(exc.message)
            }
        }
    }
}