package com.mreigar.githubusers.presentation.model

import java.io.Serializable

data class GithubUserViewEntity(
    val username: String,
    val avatarUrl: String
) : Serializable