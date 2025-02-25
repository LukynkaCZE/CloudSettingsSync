package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class AccessibilitySettings(
    val narrator: Boolean,
    val highContrast: Boolean,
    val menuBackgroundBlur: Int,
    val textBackground: TextBackground,
    val lineSpacing: Int,
    val notificationTime: Double,
    val distortionEffects: Int,
    val darknessEffects: Int,
    val glintSpeed: Int,
    val hideLightingFlashes: Boolean,
    val narratorHotKey: Boolean,
    val showSubtitles: Boolean,
    val autoJump: Boolean,
    val monochromeLogo: Boolean,
    val hideSplashTexts: Boolean
) {
    enum class TextBackground {
        CHAT,
        EVERYWHERE
    }
}