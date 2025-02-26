package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var uuid: String,
    val username: String,
    val passwordHash: String,
    var settings: Settings
)