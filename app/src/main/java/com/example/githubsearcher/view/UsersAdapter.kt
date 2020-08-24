package com.example.githubsearcher.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.githubsearcher.R
import com.example.githubsearcher.common.network.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(
    private val usersList: List<User>,
    private val glideRequestManager: RequestManager
): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = usersList[position]

        holder.bindUser(currentUser)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class UserViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {

        fun bindUser(user: User) {
            view.apply {
                tv_username.text = user.name
                tv_repos_count.text = view.context.getString(R.string.repos_count, user.reposCount)

                glideRequestManager
                    .load(user.avatarUrl)
                    .into(img_user_avatar)
            }
        }
    }
}