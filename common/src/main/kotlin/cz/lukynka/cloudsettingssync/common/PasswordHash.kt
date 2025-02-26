package cz.lukynka.cloudsettingssync.common

import java.security.MessageDigest
import java.util.*

object PasswordHash {

    fun hashPassword(password: String): String {
        return sha256(password)
    }

    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray())
        return Base64.getEncoder().encodeToString(hash)
    }
}