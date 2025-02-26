package cz.lukynka.cloudsettingssync.server.encryption

import cz.lukynka.cloudsettingssync.server.caches.ServerDataCache
import cz.lukynka.cloudsettingssync.server.caches.toKey
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

object Encryption {

    fun createToken(username: String, hashedPassword: String): String {
        val now = Instant.now()
        val expiration = now.plus(14, ChronoUnit.DAYS)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expiration))
            .claim("hash", hashedPassword)
            .signWith(ServerDataCache.get().signingToken.toKey())
            .compact()
    }

    fun decodeToken(token: String): JWTClaimState {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(ServerDataCache.get().signingToken.toKey())
                .build()
                .parseClaimsJws(token)
                .body
            JWTClaimState(claims, false)
        } catch (e: Exception) {
            return JWTClaimState(null, true)
        }
    }

    data class JWTClaimState(
        val claims: Claims?,
        val expired: Boolean
    )
}