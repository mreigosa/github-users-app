package com.mreigar.githubusersapp.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.mreigar.githubusers.presentation.model.GithubRepoViewEntity
import com.mreigar.githubusers.presentation.model.GithubUserViewEntity
import com.mreigar.githubusers.presentation.viewmodel.GithubUserDetailsViewModel
import com.mreigar.githubusersapp.R
import com.mreigar.githubusersapp.gone
import com.mreigar.githubusersapp.visible
import kotlinx.android.synthetic.main.activity_github_user_details.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class GithubUserDetailsActivity : AppCompatActivity() {

    companion object {
        const val PARAMS_ARG = "userParams"

        fun intent(context: Context, post: GithubUserViewEntity) =
            Intent(context, GithubUserDetailsActivity::class.java).apply {
                putExtra(PARAMS_ARG, post)
            }
    }

    private val viewModel: GithubUserDetailsViewModel by viewModel()

    private val githubRepoAdapter: GithubRepoAdapter by lazy { GithubRepoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userDetailsRepoList.apply {
            adapter = githubRepoAdapter
            layoutManager = LinearLayoutManager(this@GithubUserDetailsActivity)
        }

        (intent.extras?.get(PARAMS_ARG) as? GithubUserViewEntity)?.let {
            viewModel.onGithubUserFound(it)
        }

        viewModel.error.observe(this) { error ->
            if (error) showError()
        }

        viewModel.loading.observe(this) { loading ->
            if (loading) showLoading() else hideLoading()
        }

        viewModel.repos.observe(this) { githubRepos ->
            if (githubRepos.isEmpty()) showEmptyRepos() else showGithubRepos(githubRepos)
        }

        viewModel.user.observe(this) { githubUser ->
            showUser(githubUser)
        }
    }

    private fun showLoading() {
        userDetailsLoader.visible()
    }

    private fun hideLoading() {
        userDetailsLoader.gone()
    }

    private fun showUser(user: GithubUserViewEntity) {
        userDetailsUsername.text = user.username
        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions().transform(RoundedCorners(20)))
            .into(userDetailsUserAvatar)
    }

    private fun showGithubRepos(githubRepos: List<GithubRepoViewEntity>) {
        userDetailsReposTitle.text = getString(R.string.repos_list_header).format(githubRepos.size)
        githubRepoAdapter.setRepos(githubRepos)
    }

    private fun showEmptyRepos() {
        userDetailsReposTitle.text = getString(R.string.repos_list_header).format(0)
        githubRepoAdapter.setRepos(listOf())
        userDetailsRepoListEmptyText.visible()
    }

    private fun showError() {
        Snackbar.make(searchUserLayout, R.string.error_title_something_went_wrong, Snackbar.LENGTH_LONG)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
