package com.mreigar.githubusersapp.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mreigar.githubusers.presentation.model.GithubRepoViewEntity
import com.mreigar.githubusersapp.R
import kotlinx.android.synthetic.main.layout_github_repo_item.view.*

class GithubRepoAdapter : RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder>() {

    private var items: MutableList<GithubRepoViewEntity> = mutableListOf()

    fun setRepos(comments: List<GithubRepoViewEntity>) {
        items.apply {
            clear()
            addAll(comments)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder =
        GithubRepoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_github_repo_item, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class GithubRepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(githubRepo: GithubRepoViewEntity) = with(itemView) {
            itemGithubRepoName.text = githubRepo.name
            itemGithubRepoLanguage.text = githubRepo.language
        }
    }
}