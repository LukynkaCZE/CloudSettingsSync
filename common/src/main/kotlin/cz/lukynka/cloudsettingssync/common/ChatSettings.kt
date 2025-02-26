package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class ChatSettings(
    val mode: Mode,
    val webLinks: Boolean,
    val chatTextOpacity: Double,
    val chatTextSize: Double,
    val chatDelay: Double,
    val focusedHeight: Double,
    val hideMatchedNames: Boolean,
    val onlyShowSecureChat: Boolean,
    val colors: Boolean,
    val promptOnLinks: Boolean,
    val textBackgroundOpacity: Double,
    val lineSpacing: Double,
    val width: Double,
    val unfocusedHeight: Double,
    val commandSuggestions: Boolean,
    val reducedDebugInfo: Boolean
) {
    enum class Mode {
        SHOWN,
        COMMANDS_ONLY,
        HIDDEN
    }
}