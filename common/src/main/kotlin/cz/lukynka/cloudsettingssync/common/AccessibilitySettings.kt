package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class AccessibilitySettings(
    val highContrast: Boolean,
    val menuBackgroundBlur: Int,
    val backgroundForChatOnly: Boolean,
    val lineSpacing: Double,
    val notificationTime: Double,
    val distortionEffects: Double,
    val darknessEffects: Double,
    val glintSpeed: Double,
    val hideLightingFlashes: Boolean,
    val narratorHotKey: Boolean,
    val showSubtitles: Boolean,
    val autoJump: Boolean,
    val monochromeLogo: Boolean,
    val hideSplashTexts: Boolean
)