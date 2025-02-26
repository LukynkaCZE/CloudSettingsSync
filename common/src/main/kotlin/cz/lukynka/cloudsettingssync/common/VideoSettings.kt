package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class VideoSettings(
    val renderDistance: Int,
    val simulationDistance: Int,
    val brightness: Double,
    val GUIScale: Int,
    val fullscreen: Boolean,
    val vsync: Boolean,
    val maxFramerate: Int,
    val viewBobbing: Boolean,
    val attackIndicator: AttackIndicator,
    val autosaveIndicator: Boolean,
    val clouds: CloudStatus,
    val distortionEffects: Double,
    val fovEffects: Double,
) {
    enum class AttackIndicator {
        CROSSHAIR,
        HOTBAR,
        OFF
    }

    enum class CloudStatus {
        OFF,
        FAST,
        FANCY
    }
}