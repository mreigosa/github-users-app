package com.mreigar.githubusers.presentation.mapper

import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.model.GithubUser
import com.mreigar.githubusers.presentation.model.GithubRepoViewEntity
import com.mreigar.githubusers.presentation.model.GithubUserViewEntity

fun GithubUser.toViewEntity() = GithubUserViewEntity(username = username, avatarUrl = avatarUrl, name, bio, location)

fun GithubRepo.toViewEntity() = GithubRepoViewEntity(name = name, language = language)