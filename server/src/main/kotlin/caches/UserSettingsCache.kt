package cz.lukynka.cloudsettingssync.server.caches

import cz.lukynka.cloudsettingssync.common.User
import io.github.dockyard.cz.lukynka.hollow.HollowCache
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object UserSettingsCache : HollowCache<User>("users") {

    val JSON = Json { ignoreUnknownKeys = true }

    override fun serialize(value: User): String {
        return JSON.encodeToString<User>(value)
    }

    override fun deserialize(string: String): User {
        return JSON.decodeFromString<User>(string)
    }

    fun getByUsernameOrNull(username: String): User? {
        return getAll().values.firstOrNull { user -> user.username == username }
    }

    fun getByUsername(username: String): User {
        return getByUsernameOrNull(username) ?: throw IllegalArgumentException("Value with key $username was not found in the cache!")
    }
}

