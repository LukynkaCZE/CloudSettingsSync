package cz.lukynka.cloudsettingssync.server.caches

import io.github.dockyard.cz.lukynka.hollow.HollowCache
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.security.Key
import java.util.*

object ServerDataCache : HollowCache<ServerData>("server_data") {

    private val uuid = UUID.randomUUID()

    override fun serialize(value: ServerData): String {
        return UserSettingsCache.JSON.encodeToString(value)
    }

    override fun deserialize(string: String): ServerData {
        return UserSettingsCache.JSON.decodeFromString(string)
    }

    fun get(): ServerData {
        val data = getOrNull(uuid)
        if(data == null) {
            val newData = ServerData(Keys.secretKeyFor(SignatureAlgorithm.HS256).asString())
            set(uuid, newData)
            return newData
        }
        return data
    }
}

@Serializable
data class ServerData(
    val signingToken: String,
)

fun Key.asString(): String {
    return Base64.getEncoder().encodeToString(this.encoded)
}

fun String.toKey(): Key {
    val decodedKey = Base64.getDecoder().decode(this)
    return Keys.hmacShaKeyFor(decodedKey)
}