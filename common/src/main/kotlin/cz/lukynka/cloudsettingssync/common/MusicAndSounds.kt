package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class MusicAndSounds(
    val master: Float,
    val music: Float,
    val weather: Float,
    val hostileCreatures: Float,
    val players: Float,
    val voice: Float,
    val jukebox: Float,
    val blocks: Float,
    val friendlyCreatures: Float,
    val ambient: Float,
    val showSubtitles: Boolean,
    val directionalAudio: Boolean
)
