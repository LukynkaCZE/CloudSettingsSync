package cz.lukynka.cloudsettingssync.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Exception

@Serializable
data class ApiResponse<T>(
    val status: ApiResponseStatus,
    val data: T? = null
)

enum class ApiResponseStatus {
    USER_DOES_NOT_EXIST,
    USER_ALREADY_EXISTS,
    INVALID_JWT_TOKEN,
    EXPIRED_JWT_TOKEN,
    INVALID_REQUEST_FORMAT,
    INVALID_BODY_JSON,
    INCORRECT_PASSWORD,
    API_RUNTIME_EXCEPTION,
    SUCCESS,
}

inline fun <reified T> toJson(serializable: T): String {
    return Json.encodeToString<T>(serializable)
}

inline fun <reified T> fromJson(json: String): T {
    return Json.decodeFromString<T>(json)
}

inline fun <reified T> fromJsonBody(json: String): T {
    try {
        return Json.decodeFromString<T>(json)
    } catch (ex: Exception) {
        throw ApiException(ApiResponseStatus.INVALID_BODY_JSON)
    }
}

class ApiException(val error: ApiResponseStatus): Exception() {
    override val message: String
        get() = error.name
}

@Serializable
data class VoidApiResponse(
    val nothing: String? = null
)

@Serializable
data class AuthorizationResponse(
    val token: String,
)