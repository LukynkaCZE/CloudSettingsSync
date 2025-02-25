package cz.lukynka.cloudsettingssync.server.paths

import cz.lukynka.lkws.LightweightWebServer

abstract class ServerPath(val path: String) {
    abstract fun register(server: LightweightWebServer)
}