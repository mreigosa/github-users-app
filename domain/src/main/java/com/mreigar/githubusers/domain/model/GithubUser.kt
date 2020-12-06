package com.mreigar.githubusers.domain.model

data class GithubUser(
    val username: String,
    val avatarUrl: String,
    val name: String,
    val bio: String,
    val location: String
)