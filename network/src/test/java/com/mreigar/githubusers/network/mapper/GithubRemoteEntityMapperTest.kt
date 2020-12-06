package com.mreigar.githubusers.network.mapper

import com.mreigar.githubusers.data.model.GithubRepoEntity
import com.mreigar.githubusers.data.model.GithubUserEntity
import com.mreigar.githubusers.network.model.GithubRepoRemoteEntity
import com.mreigar.githubusers.network.model.GithubUserRemoteEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GithubRemoteEntityMapperTest {

    @Test
    fun `that can map a remote user entity to data entity`() {
        val remoteEntity = GithubUserRemoteEntity("id.name", "www.avatar.url", "name", "Android dev", "Spain")

        val mappedInstance: Any = remoteEntity.toDataEntity()

        assertThat(mappedInstance is GithubUserEntity).isTrue()
        assertThat((mappedInstance as GithubUserEntity).username).isEqualTo(remoteEntity.username)
        assertThat(mappedInstance.avatarUrl).isEqualTo(remoteEntity.avatarUrl)
        assertThat(mappedInstance.name).isEqualTo(remoteEntity.name)
        assertThat(mappedInstance.bio).isEqualTo(remoteEntity.bio)
        assertThat(mappedInstance.location).isEqualTo(remoteEntity.location)
    }

    @Test
    fun `that can map a remote repo entity to data entity`() {
        val remoteEntity = GithubRepoRemoteEntity("github-users-app", "Kotlin")

        val mappedInstance: Any = remoteEntity.toDataEntity()

        assertThat(mappedInstance is GithubRepoEntity).isTrue()
        assertThat((mappedInstance as GithubRepoEntity).name).isEqualTo(remoteEntity.name)
        assertThat(mappedInstance.language).isEqualTo(remoteEntity.language)
    }
}