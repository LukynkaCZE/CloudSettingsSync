package cz.lukynka.cloudsettingssync.server.caches

import cz.lukynka.cloudsettingssync.common.Settings
import io.github.dockyard.cz.lukynka.hollow.HollowCache
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

object UserSettingsCache : HollowCache<User>("users") {

    val JSON = Json { ignoreUnknownKeys = true }

    override fun serialize(value: User): String {
        return JSON.encodeToString<User>(value)
    }

    override fun deserialize(string: String): User {
        return JSON.decodeFromString<User>(string)
    }
}

data class User(
    val uuid: UUID,
    val passwordHash: String,
    val settings: Settings
)