package cz.lukynka.cloudsettingssync.server.paths

import cz.lukynka.cloudsettingssync.server.caches.User
import cz.lukynka.cloudsettingssync.server.caches.UserSettingsCache
import cz.lukynka.lkws.LightweightWebServer
import kotlinx.serialization.encodeToString
import java.util.*

class UserPath : ServerPath("/user") {

    override fun register(server: LightweightWebServer) {

        server.get("/user/{UUID}") { res ->
            val uuidString = res.URLParameters["UUID"] ?: throw IllegalArgumentException("No UUID was provided")
            val authentication = res.requestHeaders["Authorization"] ?: throw IllegalArgumentException("No authorization token provided")
            val uuid = UUID.fromString(uuidString)
            val user = UserSettingsCache.getOrNull(uuid) ?: throw java.lang.IllegalArgumentException("No user with UUID $uuid exists")
            if (authentication != user.passwordHash) throw IllegalArgumentException("Invalid authorization token provided")

            res.respondJson(UserSettingsCache.JSON.encodeToString<User>(user))
        }
    }
}