package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class MusicAndSounds(
    val master: Int,
    val music: Int,
    val weather: Int,
    val hostileCreatures: Int,
    val players: Int,
    val voice: Int,
    val jukebox: Int,
    val blocks: Int,
    val friendlyCreatures: Int,
    val ambient: Int,
    val showSubtitles: Boolean,
    val directionalAudio: Boolean
)
