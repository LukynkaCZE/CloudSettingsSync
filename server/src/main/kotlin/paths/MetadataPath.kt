package cz.lukynka.cloudsettingssync.server.paths

import cz.lukynka.cloudsettingssync.common.CloudSyncMeta
import cz.lukynka.cloudsettingssync.server.Main
import cz.lukynka.lkws.LightweightWebServer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MetadataPath: ServerPath("/metadata") {

    override fun register(server: LightweightWebServer) {
        server.get("/metadata") { res ->
            res.respond(Json.encodeToString<CloudSyncMeta>(Main.meta))
        }
    }
}