package cz.lukynka.cloudsettingssync.server

import cz.lukynka.cloudsettingssync.common.ApiException
import cz.lukynka.cloudsettingssync.common.ApiResponseStatus
import cz.lukynka.cloudsettingssync.common.CloudSyncMeta
import cz.lukynka.cloudsettingssync.server.Main.port
import cz.lukynka.cloudsettingssync.server.caches.ServerDataCache
import cz.lukynka.cloudsettingssync.server.caches.UserSettingsCache
import cz.lukynka.cloudsettingssync.server.paths.MetadataPath
import cz.lukynka.cloudsettingssync.server.paths.ServerPath
import cz.lukynka.cloudsettingssync.server.paths.UserPath
import cz.lukynka.lkws.LightweightWebServer
import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log
import io.github.dockyard.cz.lukynka.hollow.Hollow

object Main {
    val port = 6969
    val meta = CloudSyncMeta("0.1")
}

fun main() {
    val server = LightweightWebServer(port)
    val paths: List<ServerPath> = listOf(
        MetadataPath(),
        UserPath()
    )

    Hollow.initialize("db")
    ServerDataCache.initialize()
    UserSettingsCache.initialize()

    paths.forEach { path ->
        path.register(server)
        log("Registered ${path.path}", LogType.DEBUG)
    }

    server.error {
        it.responseStatusCode = 500
        val apiResponseStatus = if(it.exception is ApiException) (it.exception as ApiException).error else ApiResponseStatus.API_RUNTIME_EXCEPTION
        it.respondError(apiResponseStatus, it.exception.message)
    }

}