package com.mreigar.githubusersapp.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mreigar.githubusers.presentation.viewmodel.GithubSearUserViewModel
import com.mreigar.githubusersapp.R
import com.mreigar.githubusersapp.databinding.ActivityGithubSearchUserBinding
import com.mreigar.githubusersapp.details.GithubUserDetailsActivity
import com.mreigar.githubusersapp.gone
import com.mreigar.githubusersapp.hideKeyboard
import com.mreigar.githubusersapp.visible
import org.koin.android.viewmodel.ext.android.viewModel

class GithubSearchUserActivity : AppCompatActivity() {

    private val viewModel: GithubSearUserViewModel by viewModel()
    private lateinit var binding: ActivityGithubSearchUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_search_user)
        binding = ActivityGithubSearchUserBinding.inflate(layoutInflater).also { setContentView(it.root) }

        viewModel.error.observe(this) { error ->
            if (error) showError()
        }

        viewModel.loading.observe(this) { loading ->
            if (loading) showLoading() else hideLoading()
        }

        viewModel.user.observe(this) { githubUser ->
            startActivity(GithubUserDetailsActivity.intent(this, githubUser))
        }

        binding.searchUserButton.setOnClickListener { viewModel.onSearchClicked(binding.searchUserText.text.toString()) }
    }

    private fun showError() {
        Snackbar.make(binding.searchUserLayout, "User not found in GitHub", Snackbar.LENGTH_LONG)
    }

    private fun showLoading() {
        hideKeyboard()
        binding.searchUserLoader.visible()
    }

    private fun hideLoading() {
        binding.searchUserLoader.gone()
    }
}