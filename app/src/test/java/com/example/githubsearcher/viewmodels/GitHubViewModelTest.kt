package com.example.githubsearcher.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.githubsearcher.common.network.model.Repo
import com.example.githubsearcher.common.network.model.User
import com.example.githubsearcher.repositories.GitHubRemoteRepository
import com.example.githubsearcher.viewmodel.GitHubViewModel
import com.example.githubsearcher.viewmodels.utils.MainCoroutinesRule
import com.example.githubsearcher.viewmodels.utils.repoMock
import com.example.githubsearcher.viewmodels.utils.userMock
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GitHubViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @MockK
    private lateinit var repository: GitHubRemoteRepository

    private lateinit var viewModel: GitHubViewModel

    @SpyK
    var loadingStateObserver = Observer<GitHubViewModel.LoadingState> {  }

    @SpyK
    var userListObserver = Observer<List<User>> {  }

    @SpyK
    var userObserver = Observer<User> {  }

    @SpyK
    var reposListObserver = Observer<List<Repo>> {  }

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = GitHubViewModel(repository)

        viewModel.apply {
            loadingState.observeForever(loadingStateObserver)
            userList.observeForever(userListObserver)
            user.observeForever(userObserver)
            repoListByUser.observeForever(reposListObserver)
        }
    }

    // ===== user list =====

    @Test
    fun `getUserList successful call correctly change the value of user list`() {
        // === Setup ===
        val userList = (1..10).map { userMock.copy(name = "Name$it") }
        coEvery { repository.downloadAllUsers() } returns userList

        // === Call ===
        runBlocking { viewModel.getUserList() }

        // === Assertions ===
        verify { userListObserver.onChanged(userList) }
        assertEquals(userList, viewModel.userList.value)
    }

    @Test
    fun `getUserList successful call correctly change the value of loading state to success`() {
        // === Setup ===
        coEvery { repository.downloadAllUsers() } returns listOf()

        // === Call ===
        runBlocking { viewModel.getUserList() }

        // === Assertions ===
        verify(ordering = Ordering.ORDERED) {
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.LOADING)
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.SUCCESS)
        }
        assertEquals(GitHubViewModel.LoadingState.SUCCESS, viewModel.loadingState.value)
    }

    @Test
    fun `getUserList unsuccessful call correctly change the value of loading state to error`() {
        // === Setup ===
        val errorMessage = "error"
        coEvery { repository.downloadAllUsers() } throws RuntimeException(errorMessage)

        // === Call ===
        runBlocking { viewModel.getUserList() }

        // === Assertions ===
        verify(ordering = Ordering.ORDERED) {
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.LOADING)
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.ERROR(errorMessage))
        }
        assertEquals(GitHubViewModel.LoadingState.ERROR(errorMessage), viewModel.loadingState.value)
    }

    // ===== user =====

    @Test
    fun `getUser successful call correctly change the value of user`() {
        // === Setup ===
        val username = "username"
        coEvery { repository.downloadUser(username) } returns userMock

        // === Call ===
        runBlocking { viewModel.getUser(username) }

        // === Assertions ===
        verify { userObserver.onChanged(userMock) }
        assertEquals(userMock, viewModel.user.value)
    }

    @Test
    fun `getUser successful call correctly change the value of loading state to success`() {
        // === Setup ===
        val username = "username"
        coEvery { repository.downloadUser(username) } returns mockk()

        // === Call ===
        runBlocking { viewModel.getUser(username) }

        // === Assertions ===
        verify(ordering = Ordering.ORDERED) {
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.LOADING)
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.SUCCESS)
        }
        assertEquals(GitHubViewModel.LoadingState.SUCCESS, viewModel.loadingState.value)
    }

    @Test
    fun `getUser unsuccessful call correctly change the value of loading state to error`() {
        // === Setup ===
        val username = "username"
        val errorMessage = "error"
        coEvery { repository.downloadUser(username) } throws RuntimeException(errorMessage)

        // === Call ===
        runBlocking { viewModel.getUser(username) }

        // === Assertions ===
        verify(ordering = Ordering.ORDERED) {
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.LOADING)
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.ERROR(errorMessage))
        }
        assertEquals(GitHubViewModel.LoadingState.ERROR(errorMessage), viewModel.loadingState.value)
    }

    // ===== repos by user =====

    @Test
    fun `getReposByUser successful call correctly change the value of repos list`() {
        // === Setup ===
        val username = "username"
        val repoList = (1..10).map { repoMock.copy(name = "Name$it") }
        coEvery { repository.downloadReposByUsername(username) } returns repoList

        // === Call ===
        runBlocking { viewModel.getReposByUser(username) }

        // === Assertions ===
        verify { reposListObserver.onChanged(repoList) }
        assertEquals(repoList, viewModel.repoListByUser.value)
    }

    @Test
    fun `getReposByUser successful call correctly change the value of loading state to success`() {
        // === Setup ===
        val username = "username"
        coEvery { repository.downloadReposByUsername(username) } returns listOf()

        // === Call ===
        runBlocking { viewModel.getReposByUser(username) }

        // === Assertions ===
        verify(ordering = Ordering.ORDERED) {
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.LOADING)
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.SUCCESS)
        }
        assertEquals(GitHubViewModel.LoadingState.SUCCESS, viewModel.loadingState.value)
    }

    @Test
    fun `getReposByUser unsuccessful call correctly change the value of loading state to error`() {
        // === Setup ===
        val username = "username"
        val errorMessage = "error"
        coEvery { repository.downloadReposByUsername(username) } throws RuntimeException(errorMessage)

        // === Call ===
        runBlocking { viewModel.getReposByUser(username) }

        // === Assertions ===
        verify(ordering = Ordering.ORDERED) {
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.LOADING)
            loadingStateObserver.onChanged(GitHubViewModel.LoadingState.ERROR(errorMessage))
        }
        assertEquals(GitHubViewModel.LoadingState.ERROR(errorMessage), viewModel.loadingState.value)
    }
}