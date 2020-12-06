package com.mreigar.githubusers.data.mapper

import com.mreigar.githubusers.data.model.GithubRepoEntity
import com.mreigar.githubusers.data.model.GithubUserEntity
import com.mreigar.githubusers.domain.model.GithubRepo
import com.mreigar.githubusers.domain.model.GithubUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GithubEntityMapperTest {

    @Test
    fun `that can map a data user entity to domain entity`() {
        val dataEntity = GithubUserEntity("id.name", "www.avatar.url", "name", "Android dev", "Spain")

        val mappedInstance: Any = dataEntity.toDomainEntity()

        assertThat(mappedInstance is GithubUser).isTrue()
        assertThat((mappedInstance as GithubUser).username).isEqualTo(dataEntity.username)
        assertThat(mappedInstance.avatarUrl).isEqualTo(dataEntity.avatarUrl)
        assertThat(mappedInstance.name).isEqualTo(dataEntity.name)
        assertThat(mappedInstance.bio).isEqualTo(dataEntity.bio)
        assertThat(mappedInstance.location).isEqualTo(dataEntity.location)
    }

    @Test
    fun `that can map a data repo entity to domain entity`() {
        val dataEntity = GithubRepoEntity("github-users-app", "Kotlin")

        val mappedInstance: Any = dataEntity.toDomainEntity()

        assertThat(mappedInstance is GithubRepo).isTrue()
        assertThat((mappedInstance as GithubRepo).name).isEqualTo(dataEntity.name)
        assertThat(mappedInstance.language).isEqualTo(dataEntity.language)
    }
}