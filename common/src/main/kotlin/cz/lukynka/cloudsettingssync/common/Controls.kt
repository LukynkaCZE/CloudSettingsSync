package cz.lukynka.cloudsettingssync.common

data class Controls(
    val mouseSettings: MouseSettings,
    val sneak: SneakSprintAction,
    val autoJump: Boolean,
    val sprint: SneakSprintAction,
    val operatorItemsTab: Boolean,
    val keybinds: Map<String, String>
) {
    data class MouseSettings(
        val sensitivity: Int,
        val scrollSensitivity: Double,
        val invertMouse: Boolean,
        val rawInput: Boolean,
        val discreteScrolling: Boolean
    )

    enum class SneakSprintAction {
        HOLD,
        TOGGLE
    }
}