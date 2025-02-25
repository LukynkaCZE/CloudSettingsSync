package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class ChatSettings(
    val mode: Mode,
    val webLinks: Boolean,
    val chatTextOpacity: Int,
    val chatTextSize: Int,
    val chatDelay: Double,
    val focusedHeight: Int,
    val narrator: Boolean,
    val hideMatchedNames: Boolean,
    val onlyShowSecureChat: Boolean,
    val colors: Boolean,
    val promptOnLinks: Boolean,
    val textBackgroundOpacity: Int,
    val lineSpacing: Int,
    val width: Int,
    val unfocusedHeight: Int,
    val commandSuggestions: Boolean,
    val reducedDebugInfo: Boolean
) {
    enum class Mode {
        SHOWN,
        COMMANDS_ONLY,
        HIDDEN
    }
}