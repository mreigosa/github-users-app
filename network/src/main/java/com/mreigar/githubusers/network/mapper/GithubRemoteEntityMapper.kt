package com.mreigar.githubusers.network.mapper

import com.mreigar.githubusers.data.model.GithubRepoEntity
import com.mreigar.githubusers.data.model.GithubUserEntity
import com.mreigar.githubusers.network.model.GithubRepoRemoteEntity
import com.mreigar.githubusers.network.model.GithubUserRemoteEntity

fun GithubUserRemoteEntity.toDataEntity() = GithubUserEntity(username = username, avatarUrl = avatarUrl)

fun GithubRepoRemoteEntity.toDataEntity() = GithubRepoEntity(name = name, language = language.orEmpty())