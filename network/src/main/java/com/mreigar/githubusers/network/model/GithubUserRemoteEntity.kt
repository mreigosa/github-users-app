package com.mreigar.githubusers.network.model

import com.google.gson.annotations.SerializedName

data class GithubUserRemoteEntity(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("bio") val bio: String
)