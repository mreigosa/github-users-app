package com.mreigar.githubusersapp.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mreigar.githubusers.presentation.viewmodel.GithubSearUserViewModel
import com.mreigar.githubusersapp.R
import com.mreigar.githubusersapp.details.GithubUserDetailsActivity
import com.mreigar.githubusersapp.gone
import com.mreigar.githubusersapp.hideKeyboard
import com.mreigar.githubusersapp.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class GithubSearchUserActivity : AppCompatActivity() {

    private val viewModel: GithubSearUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.error.observe(this) { error ->
            if (error) showError()
        }

        viewModel.loading.observe(this) { loading ->
            if (loading) showLoading() else hideLoading()
        }

        viewModel.user.observe(this) { githubUser ->
            startActivity(GithubUserDetailsActivity.intent(this, githubUser))
        }

        searchUserButton.setOnClickListener { viewModel.onSearchClicked(searchUserText.text.toString()) }
    }

    private fun showError() {
        Snackbar.make(searchUserLayout, "User not found in GitHub", Snackbar.LENGTH_LONG)
    }

    private fun showLoading() {
        hideKeyboard()
        searchUserLoader.visible()
    }

    private fun hideLoading() {
        searchUserLoader.gone()
    }
}