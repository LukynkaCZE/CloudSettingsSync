package cz.lukynka.cloudsettingssync.fabric.client

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.toasts.SystemToast
import net.minecraft.client.gui.components.toasts.SystemToast.SystemToastId
import net.minecraft.network.chat.Component

object CloudSettingsToastManager {

    val CLOUD_SETTINGS_SYNC: SystemToastId = SystemToastId(2000L)

    enum class ToastType(val text: Component) {
        LOGIN_NOT_LOGGED_IN(Component.literal("You are not logged in")),
        LOGIN_SUCCESS(Component.literal("Successfully Logged In").withStyle(ChatFormatting.GREEN)),
        LOGIN_FAIL(Component.literal("Logging in has failed").withStyle(ChatFormatting.RED)),
        SYNC_SUCCESS(Component.literal("Settings have been synced with cloud").withStyle(ChatFormatting.GREEN)),
        SYNC_FAIL(Component.literal("Sync with cloud has failed").withStyle(ChatFormatting.RED)),
        CLOUD_UPDATE_SUCCESS(Component.literal("Cloud settings have been updated").withStyle(ChatFormatting.GREEN)),
        CLOUD_UPDATE_FAIL(Component.literal("Updating cloud settings has failed").withStyle(ChatFormatting.RED)),
    }

    fun sendToast(type: ToastType) {
        val toast = SystemToast(CLOUD_SETTINGS_SYNC, Component.literal("Cloud Settings Sync"), type.text)
        Minecraft.getInstance().toastManager.addToast(toast)
    }
}