package com.mreigar.githubusers.data.mapper

import com.mreigar.githubusers.data.model.GithubRepoEntity
import com.mreigar.githubusers.data.model.GithubUserEntity
import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.model.GithubUser

fun GithubUserEntity.toDomainEntity() = GithubUser(username = username, avatarUrl = avatarUrl)

fun GithubRepoEntity.toDomainEntity() = GithubRepo(name = name, language = language)