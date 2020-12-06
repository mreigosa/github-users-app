package com.mreigar.githubusers.network.model

import com.google.gson.annotations.SerializedName

data class GithubRepoRemoteEntity (
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: String?
)