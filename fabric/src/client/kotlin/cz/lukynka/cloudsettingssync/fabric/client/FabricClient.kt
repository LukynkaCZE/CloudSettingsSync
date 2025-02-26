package cz.lukynka.cloudsettingssync.fabric.client

import cz.lukynka.cloudsettingssync.common.ApiException
import cz.lukynka.cloudsettingssync.fabric.client.screens.ExtendedOptionsScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents

class FabricClient : ClientModInitializer {


    override fun onInitializeClient() {

        ClientLifecycleEvents.CLIENT_STARTED.register { client ->
            ExtendedOptionsScreen.register()
        }
    }

    companion object {

        const val host = "http://localhost:6969"
        var jwtToken: String? = null
        var user: String? = null
        var passwordLength = 0

        @JvmStatic
        fun mainMenuLoadCallback() {
        }

        @JvmStatic
        fun syncSettingsFromCloud() {
            if (user == null) return
            if (jwtToken == null) return
            Networking.getSettings(user!!, jwtToken!!)
                .thenAccept { res ->
                    ClientSettingsSetter.set(res.settings)
                    CloudSettingsToastManager.sendToast(CloudSettingsToastManager.ToastType.SYNC_SUCCESS)
                }
                .exceptionally { exception ->
                    CloudSettingsToastManager.sendToast(CloudSettingsToastManager.ToastType.SYNC_FAIL)
                    if (exception.cause!! is ApiException) {
                        exception.printStackTrace()
                    }
                    null
                }
        }

        @JvmStatic
        fun updateCloudSettings() {
            if (user == null) return
            if (jwtToken == null) return
            Networking.setSettings(user!!, jwtToken!!, ClientSettingsGetter.getSettings())
                .thenApply { res ->
//                    CloudSettingsToastManager.sendToast(CloudSettingsToastManager.ToastType.CLOUD_UPDATE_SUCCESS)
                }
                .exceptionally { exception ->
                    CloudSettingsToastManager.sendToast(CloudSettingsToastManager.ToastType.CLOUD_UPDATE_FAIL)
                    if (exception.cause!! is ApiException) {
                        exception.printStackTrace()
                    }
                }
        }
    }
}