package cz.lukynka.cloudsettingssync.server

import cz.lukynka.cloudsettingssync.common.CloudSyncMeta
import cz.lukynka.cloudsettingssync.server.Main.port
import cz.lukynka.cloudsettingssync.server.paths.MetadataPath
import cz.lukynka.cloudsettingssync.server.paths.ServerPath
import cz.lukynka.cloudsettingssync.server.paths.UserPath
import cz.lukynka.lkws.LightweightWebServer
import cz.lukynka.prettylog.LogType
import cz.lukynka.prettylog.log

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

    paths.forEach { path ->
        path.register(server)
        log("Registered ${path.path}", LogType.DEBUG)
    }

}