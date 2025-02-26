package cz.lukynka.cloudsettingssync.fabric.client.screens

import cz.lukynka.cloudsettingssync.common.ApiException
import cz.lukynka.cloudsettingssync.fabric.client.ClientSettingsGetter
import cz.lukynka.cloudsettingssync.fabric.client.CloudSettingsToastManager
import cz.lukynka.cloudsettingssync.fabric.client.FabricClient
import cz.lukynka.cloudsettingssync.fabric.client.Networking
import io.wispforest.owo.ui.base.BaseOwoScreen
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.Components
import io.wispforest.owo.ui.component.LabelComponent
import io.wispforest.owo.ui.container.Containers
import io.wispforest.owo.ui.container.FlowLayout
import io.wispforest.owo.ui.core.*
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

class SettingScreen : BaseOwoScreen<FlowLayout>() {

    companion object {
        private val COMPONENT_LOGGED_IN = Component.literal("Successfully Authenticated").withStyle(ChatFormatting.GREEN)
        private val COMPONENT_LOGGED_OUT = Component.literal(("Logged out")).withStyle(ChatFormatting.YELLOW)
        private val COMPONENT_AUTHENTICATING = Component.literal("Authenticating..").withStyle(ChatFormatting.GRAY)
        private val COMPONENT_EMPTY = Component.literal("")
    }

    override fun createAdapter(): OwoUIAdapter<FlowLayout> {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    private val usernameBox = Components.textBox(Sizing.fixed(162))
    private val passwordBox = Components.textBox(Sizing.fixed(162))

    private val buttonsContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content())
    private val statusLabel = Components.label(COMPONENT_EMPTY)

    private val logOutButton = button("Log Out") { button ->
        FabricClient.jwtToken = null
        FabricClient.user = null
        statusLabel.text(COMPONENT_LOGGED_OUT)
        updateLoginButtonState()
        ExtendedOptionsScreen.updateState()
        CloudSettingsToastManager.sendToast(CloudSettingsToastManager.ToastType.LOGIN_NOT_LOGGED_IN)
    }

    private val logInButton = button("Authenticate") { button ->
        statusLabel.text(COMPONENT_AUTHENTICATING)
        Networking.getOrCreate(usernameBox.value, passwordBox.value, ClientSettingsGetter.getSettings())
            .thenAccept { res ->
                statusLabel.text()
                FabricClient.jwtToken = res.token
                FabricClient.user = usernameBox.value
                FabricClient.syncSettingsFromCloud()
                FabricClient.passwordLength = passwordBox.value.length
                updateLoginButtonState()
                updateStatusLabelState()
                statusLabel.text(COMPONENT_LOGGED_IN)
                ExtendedOptionsScreen.updateState()
                CloudSettingsToastManager.sendToast(CloudSettingsToastManager.ToastType.LOGIN_SUCCESS)
            }
            .exceptionally { exception ->
                CloudSettingsToastManager.sendToast(CloudSettingsToastManager.ToastType.LOGIN_FAIL)
                if (exception.cause!! is ApiException) {
                    exception.printStackTrace()
                    statusLabel.text(Component.literal((exception.cause as ApiException).error.name).withStyle(ChatFormatting.RED))
                }
                null
            }
    }

    override fun build(root: FlowLayout) {
        root.surface(Surface.OPTIONS_BACKGROUND);
        root.horizontalAlignment(HorizontalAlignment.CENTER)
        root.verticalAlignment(VerticalAlignment.CENTER)
        root.gap(6)

        val text = if (FabricClient.user != null) FabricClient.user!! else Minecraft.getInstance().user.name
        usernameBox.text(text)

        buttonsContainer.gap(6)
        buttonsContainer.children(
            listOf(
                logInButton,
                logOutButton
            )
        )

        root.children(
            listOf(
                label("Login"),
                usernameBox,
                passwordBox,
                buttonsContainer,
                statusLabel
            )
        )

        updateLoginButtonState()
        updateStatusLabelState()
    }

    private fun updateLoginButtonState() {
        logOutButton.active((FabricClient.user != null && FabricClient.jwtToken != null))
        logInButton.active(!(FabricClient.user != null && FabricClient.jwtToken != null))
    }

    private fun updateStatusLabelState() {
        if (FabricClient.jwtToken != null && FabricClient.user != null) {
            statusLabel.text(COMPONENT_LOGGED_IN)
            var fakePwdString = ""
            repeat(FabricClient.passwordLength) { fakePwdString += "*" }
            passwordBox.text(fakePwdString)
        }
    }
}

fun label(message: String): LabelComponent {
    return Components.label(Component.literal(message))
}

fun button(message: String, unit: (ButtonComponent) -> Unit): ButtonComponent {
    return Components.button(Component.literal(message), unit)
}
