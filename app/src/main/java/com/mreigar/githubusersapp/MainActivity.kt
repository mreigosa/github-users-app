package com.mreigar.githubusersapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mreigar.githubusers.presentation.viewmodel.GithubSearUserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

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
            Toast.makeText(this@MainActivity, "User found: ${githubUser.username}", Toast.LENGTH_LONG).show()
        }

        searchUserButton.setOnClickListener { viewModel.onSearchClicked(searchUserText.text.toString()) }
    }

    fun showError() {
        Snackbar.make(searchUserLayout, "User not found in GitHub", Snackbar.LENGTH_LONG)
    }

    fun showLoading() {
        hideKeyboard()
        searchUserLoader.visible()
    }

    fun hideLoading() {
        searchUserLoader.gone()
    }
}