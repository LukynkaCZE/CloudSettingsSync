package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable

@Serializable
data class SkinCustomization(
    val cape: Boolean,
    val leftSleeve: Boolean,
    val leftPantsLeg: Boolean,
    val hat: Boolean,
    val jacket: Boolean,
    val rightPantsLeg: Boolean,
    val rightSleeve: Boolean,
    val mainHand: Hand
) {
    @Serializable
    enum class Hand {
        RIGHT,
        LEFT
    }
}