package cz.lukynka.cloudsettingssync.server.paths

import cz.lukynka.cloudsettingssync.common.*
import cz.lukynka.cloudsettingssync.server.caches.UserSettingsCache
import cz.lukynka.cloudsettingssync.server.encryption.Encryption
import cz.lukynka.cloudsettingssync.server.respondSerialized
import cz.lukynka.lkws.LightweightWebServer
import cz.lukynka.prettylog.log
import kotlinx.serialization.decodeFromString
import java.time.Instant
import java.util.*

class UserPath : ServerPath("/user") {

    override fun register(server: LightweightWebServer) {

        server.get("/user/exists/{USERNAME}") { res ->
            val username = res.URLParameters["USERNAME"] ?: throw ApiException(ApiResponseStatus.INVALID_REQUEST_FORMAT)
            res.respondSerialized<Boolean>(UserSettingsCache.getByUsernameOrNull(username) != null)
        }

        server.get("/user/{USERNAME}") { res ->
            val username = res.URLParameters["USERNAME"] ?: throw ApiException(ApiResponseStatus.INVALID_REQUEST_FORMAT)
            val authorization = res.requestHeaders["Authorization"] ?: throw ApiException(ApiResponseStatus.INVALID_REQUEST_FORMAT)
            val user = UserSettingsCache.getByUsernameOrNull(username) ?: throw ApiException(ApiResponseStatus.USER_DOES_NOT_EXIST)

            val token = Encryption.decodeToken(authorization)
            if (token.claims == null && !token.expired) throw ApiException(ApiResponseStatus.INVALID_JWT_TOKEN)
            if (token.expired) throw ApiException(ApiResponseStatus.EXPIRED_JWT_TOKEN)

            val expirationTime = token.claims!!.expiration.toInstant()
            if (Instant.now().isAfter(expirationTime)) throw ApiException(ApiResponseStatus.EXPIRED_JWT_TOKEN)

            val passwordHash = token.claims["hash"] as String
            if (passwordHash != user.passwordHash) throw ApiException(ApiResponseStatus.INVALID_JWT_TOKEN)

            res.respondSerialized(user)
        }

        server.get("/user/authorize/{USERNAME}") { res ->
            val username = res.URLParameters["USERNAME"] ?: throw ApiException(ApiResponseStatus.INVALID_REQUEST_FORMAT)
            val authorization = res.requestHeaders["Authorization"] ?: throw ApiException(ApiResponseStatus.INVALID_REQUEST_FORMAT)
            val user = UserSettingsCache.getByUsernameOrNull(username) ?: throw ApiException(ApiResponseStatus.USER_DOES_NOT_EXIST)
            if(user.passwordHash != authorization) throw ApiException(ApiResponseStatus.INCORRECT_PASSWORD)

            val token = Encryption.createToken(username, authorization)
            res.respondSerialized(AuthorizationResponse(token))
        }

        server.post("/user/{USERNAME}") { res ->
            val username = res.URLParameters["USERNAME"] ?: throw ApiException(ApiResponseStatus.INVALID_REQUEST_FORMAT)
            val user = UserSettingsCache.getByUsernameOrNull(username) ?: throw ApiException(ApiResponseStatus.USER_DOES_NOT_EXIST)
            val body = UserSettingsCache.JSON.decodeFromString<Settings>(res.requestBody)

            UserSettingsCache[UUID.fromString(user.uuid)] = user.apply { settings = body }

            res.respondSerialized(true)
        }

        server.put("/user/{USERNAME}") { res ->
            val username = res.URLParameters["USERNAME"] ?: throw ApiException(ApiResponseStatus.INVALID_REQUEST_FORMAT)
            val user = UserSettingsCache.getByUsernameOrNull(username)
            if (user != null) throw ApiException(ApiResponseStatus.USER_ALREADY_EXISTS)
            val body = UserSettingsCache.JSON.decodeFromString<User>(res.requestBody)
            val uuid = UUID.randomUUID()
            body.uuid = uuid.toString()
            UserSettingsCache[uuid] = body

            val authorization = Encryption.createToken(username, body.passwordHash)

            res.respondSerialized(AuthorizationResponse(authorization))
        }

        server.get("/hash/{PWD}") { res ->
            val password = res.URLParameters["PWD"]!!
            res.respond(PasswordHash.hashPassword(password))
        }
    }
}