package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class VideoSettings(
    val renderDistance: Int,
    val simulationDistance: Int,
    val brightness: Int,
    val GUIScale: Int,
    val fullscreen: Boolean,
    val vsync: Boolean,
    val maxFramerate: Int,
    val viewBobbing: Boolean,
    val attackIndicator: AttackIndicator,
    val autosaveIndicator: Boolean,
    val clouds: Boolean,
    val vignette: Boolean,
    val distortionEffects: Int,
    val fovEffects: Int,
) {
    enum class AttackIndicator {
        CROSSHAIR,
        HOTBAR,
        OFF
    }
}