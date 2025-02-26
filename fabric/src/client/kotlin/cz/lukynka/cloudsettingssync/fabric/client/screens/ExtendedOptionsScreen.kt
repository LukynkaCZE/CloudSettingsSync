package cz.lukynka.cloudsettingssync.fabric.client.screens

import cz.lukynka.cloudsettingssync.fabric.client.FabricClient
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.Containers
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.HorizontalAlignment
import io.wispforest.owo.ui.core.Insets
import io.wispforest.owo.ui.core.Sizing
import io.wispforest.owo.ui.core.VerticalAlignment
import io.wispforest.owo.ui.layers.Layers
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.options.OptionsScreen
import net.minecraft.network.chat.Component

object ExtendedOptionsScreen {

    private val COMPONENT_LOGGED_IN = Component.literal("Synced with cloud").withStyle(ChatFormatting.GREEN)
    private val COMPONENT_LOGGED_OUT = Component.literal(("Not Logged In")).withStyle(ChatFormatting.YELLOW)

    private val cloudSyncButton: ButtonComponent = Components.button(Component.literal("Cloud Settings Sync")) { buttonComponent: ButtonComponent? ->
        Minecraft.getInstance().setScreen(SettingScreen())
    }

    private val cloudSettingsSyncFlow: FlowLayout = Containers.verticalFlow(Sizing.content(), Sizing.content())

    private val statusLabel: LabelComponent = Components.label(COMPONENT_LOGGED_OUT)

    fun updateState() {
        val component = if(FabricClient.user != null && FabricClient.jwtToken != null) COMPONENT_LOGGED_IN else COMPONENT_LOGGED_OUT
        statusLabel.text(component)
    }

    fun register() {

        cloudSettingsSyncFlow.gap(6)
        cloudSettingsSyncFlow.alignment(HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
        cloudSettingsSyncFlow.padding(Insets.both(6, 6))

        cloudSettingsSyncFlow.children(listOf(
            statusLabel,
            cloudSyncButton
        ))

        Layers.add({ horizontalSizing: Sizing, verticalSizing: Sizing ->
            val flow = Containers.verticalFlow(horizontalSizing, verticalSizing)
            flow.alignment(HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
            flow
        },
            { instance ->
                instance.adapter.rootComponent.child(cloudSettingsSyncFlow)
            }, OptionsScreen::class.java
        )
    }
}