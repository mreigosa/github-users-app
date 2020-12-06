package com.mreigar.githubusersapp.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mreigar.githubusers.presentation.model.GithubRepoViewEntity
import com.mreigar.githubusersapp.databinding.LayoutGithubRepoItemBinding

class GithubRepoAdapter : RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder>() {

    private var items: MutableList<GithubRepoViewEntity> = mutableListOf()

    fun setRepos(comments: List<GithubRepoViewEntity>) {
        items.apply {
            clear()
            addAll(comments)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder {
        val binding = LayoutGithubRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubRepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) = holder.bind(items[position])

    class GithubRepoViewHolder(
        private val binding: LayoutGithubRepoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(githubRepo: GithubRepoViewEntity) = with(itemView) {
            binding.itemGithubRepoName.text = githubRepo.name
            binding.itemGithubRepoLanguage.text = githubRepo.language
        }
    }
}