package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class Server(
    val name: String,
    val address: String,
    val resourcePackMode: ResourcePackMode
) {
    enum class ResourcePackMode {
        PROMPT,
        ENABLED,
        DISABLED,
    }
}
