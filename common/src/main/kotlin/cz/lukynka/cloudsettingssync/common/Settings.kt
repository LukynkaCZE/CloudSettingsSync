package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val fov: Int,
    val skinCustomization: SkinCustomization,
    val videoSettings: VideoSettings,
    val language: String,
    val musicAndSounds: MusicAndSounds,
    val chatSettings: ChatSettings,
    val accessibilitySettings: AccessibilitySettings,
    val latestCloudSettingsSyncVersion: String,
    val server: List<Server>
)