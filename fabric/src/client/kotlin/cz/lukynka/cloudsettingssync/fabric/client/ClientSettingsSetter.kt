package cz.lukynka.cloudsettingssync.fabric.client

import cz.lukynka.cloudsettingssync.common.Settings
import cz.lukynka.cloudsettingssync.common.SkinCustomization
import cz.lukynka.cloudsettingssync.common.VideoSettings
import net.minecraft.client.AttackIndicatorStatus
import net.minecraft.client.CloudStatus
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.entity.player.PlayerModelPart

object ClientSettingsSetter {

    private val settings = Minecraft.getInstance().options

    fun set(new: Settings) {
        settings.fov().set(new.fov)
        setSkinSettings(new.skinCustomization)
        setVideoSettings(new.videoSettings)
        settings.save()
    }

    fun setVideoSettings(new: VideoSettings) {
        settings.renderDistance().set(new.renderDistance)
        settings.simulationDistance().set(new.simulationDistance)
        settings.gamma().set(new.brightness)
        settings.guiScale().set(new.GUIScale)
        settings.fullscreen().set(new.fullscreen)
        settings.enableVsync().set(new.vsync)
        settings.framerateLimit().set(new.maxFramerate)
        settings.bobView().set(new.viewBobbing)
        settings.attackIndicator().set(new.attackIndicator.toVanilla())
        settings.showAutosaveIndicator().set(new.autosaveIndicator)
        settings.cloudStatus().set(new.clouds.toVanilla())
        settings.screenEffectScale().set(new.distortionEffects)
        settings.fovEffectScale().set(new.fovEffects)
    }

    fun setSkinSettings(new: SkinCustomization) {
        settings.setModelPart(PlayerModelPart.CAPE, new.cape)
        settings.setModelPart(PlayerModelPart.LEFT_SLEEVE, new.leftSleeve)
        settings.setModelPart(PlayerModelPart.LEFT_PANTS_LEG, new.leftPantsLeg)
        settings.setModelPart(PlayerModelPart.HAT, new.hat)
        settings.setModelPart(PlayerModelPart.JACKET, new.jacket)
        settings.setModelPart(PlayerModelPart.RIGHT_PANTS_LEG, new.rightPantsLeg)
        settings.setModelPart(PlayerModelPart.RIGHT_SLEEVE, new.rightSleeve)
        settings.mainHand().set(new.mainHand.toHumanoidArm())
    }

    fun VideoSettings.CloudStatus.toVanilla(): CloudStatus {
        return when (this) {
            VideoSettings.CloudStatus.OFF -> CloudStatus.OFF
            VideoSettings.CloudStatus.FAST -> CloudStatus.FAST
            VideoSettings.CloudStatus.FANCY -> CloudStatus.FANCY
        }
    }

    fun VideoSettings.AttackIndicator.toVanilla(): AttackIndicatorStatus {
        return when (this) {
            VideoSettings.AttackIndicator.CROSSHAIR -> AttackIndicatorStatus.CROSSHAIR
            VideoSettings.AttackIndicator.HOTBAR -> AttackIndicatorStatus.HOTBAR
            VideoSettings.AttackIndicator.OFF -> AttackIndicatorStatus.OFF
        }
    }

    fun SkinCustomization.Hand.toHumanoidArm(): HumanoidArm {
        return when (this) {
            SkinCustomization.Hand.RIGHT -> HumanoidArm.RIGHT
            SkinCustomization.Hand.LEFT -> HumanoidArm.LEFT
        }
    }
}