package cz.lukynka.cloudsettingssync.fabric.client

import cz.lukynka.cloudsettingssync.common.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers
import java.util.*
import java.util.concurrent.CompletableFuture

object Networking {

    const val URL_AUTHORIZE = "${FabricClient.host}/user/authorize"
    const val URL_USER_EXISTS = "${FabricClient.host}/user/exists"
    const val URL_USER = "${FabricClient.host}/user"

    val httpClient = HttpClient.newHttpClient()

    fun setSettings(username: String, jwtToken: String, settings: Settings): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        CompletableFuture.runAsync {
            val request = HttpRequest.newBuilder()
                .uri(URI("${URL_USER}/$username"))
                .setHeader("Authorization", jwtToken)
                .POST(BodyPublishers.ofString(toJson(settings)))
                .build()

            val res = httpClient.send(request, BodyHandlers.ofString())
            val response = fromJson<ApiResponse<Boolean>>(res.body())

            when (response.status) {
                ApiResponseStatus.USER_ALREADY_EXISTS,
                ApiResponseStatus.API_RUNTIME_EXCEPTION,
                ApiResponseStatus.INVALID_JWT_TOKEN,
                ApiResponseStatus.INVALID_REQUEST_FORMAT,
                ApiResponseStatus.EXPIRED_JWT_TOKEN,
                ApiResponseStatus.INVALID_BODY_JSON,
                -> throw ApiException(response.status)

                else -> {}
            }
            future.complete(response.data)
        }
        return future
    }

    fun getSettings(username: String, jwtToken: String): CompletableFuture<User> {
        val future = CompletableFuture<User>()

        CompletableFuture.runAsync {
            val request = HttpRequest.newBuilder()
                .uri(URI("${URL_USER}/$username"))
                .setHeader("Authorization", jwtToken)
                .GET()
                .build()

            val res = httpClient.send(request, BodyHandlers.ofString())
            val response = fromJson<ApiResponse<User>>(res.body())

            when (response.status) {
                ApiResponseStatus.USER_ALREADY_EXISTS,
                ApiResponseStatus.API_RUNTIME_EXCEPTION,
                ApiResponseStatus.INVALID_JWT_TOKEN,
                ApiResponseStatus.INVALID_REQUEST_FORMAT,
                ApiResponseStatus.EXPIRED_JWT_TOKEN,
                -> throw ApiException(response.status)

                else -> {}
            }
            future.complete(response.data)
        }

        return future
    }

    fun getOrCreate(username: String, password: String, settings: Settings): CompletableFuture<AuthorizationResponse> {
        val future = CompletableFuture<AuthorizationResponse>()
        CompletableFuture.runAsync {
            val request = HttpRequest.newBuilder()
                .uri(URI("${URL_USER_EXISTS}/$username"))
                .GET()
                .build()

            val res = httpClient.send(request, BodyHandlers.ofString())
            val exists = fromJson<ApiResponse<Boolean>>(res.body()).data!!

            if (exists) {
                future.complete(getAuthentication(username, password))
            } else {
                future.complete(createUser(username, password, settings))
            }
        }.exceptionally { ex ->
            future.completeExceptionally(ex)
            null
        }

        return future
    }

    fun createUser(username: String, password: String, settings: Settings): AuthorizationResponse {
        val hashedPassword = PasswordHash.hashPassword(password)
        val user = User(
            uuid = UUID.randomUUID().toString(),
            username = username,
            passwordHash = hashedPassword,
            settings = settings,
        )

        val request = HttpRequest.newBuilder()
            .uri(URI("$URL_USER/$username"))
            .setHeader("Authorization", hashedPassword)
            .PUT(BodyPublishers.ofString(toJson(user)))
            .build()

        val res = httpClient.send(request, BodyHandlers.ofString())
        val response = fromJson<ApiResponse<AuthorizationResponse>>(res.body())

        when (response.status) {
            ApiResponseStatus.USER_ALREADY_EXISTS,
            ApiResponseStatus.INVALID_REQUEST_FORMAT,
            ApiResponseStatus.INVALID_BODY_JSON,
            ApiResponseStatus.API_RUNTIME_EXCEPTION,
            -> throw ApiException(response.status)

            else -> {}
        }
        return response.data!!
    }

    fun getAuthentication(username: String, password: String): AuthorizationResponse {
        val hashedPassword = PasswordHash.hashPassword(password)

        val request = HttpRequest.newBuilder()
            .uri(URI("$URL_AUTHORIZE/$username"))
            .setHeader("Authorization", hashedPassword)
            .GET()
            .build()

        val res = httpClient.send(request, BodyHandlers.ofString())
        val response = fromJson<ApiResponse<AuthorizationResponse>>(res.body())

        when (response.status) {
            ApiResponseStatus.USER_DOES_NOT_EXIST,
            ApiResponseStatus.INVALID_REQUEST_FORMAT,
            ApiResponseStatus.INCORRECT_PASSWORD,
            ApiResponseStatus.API_RUNTIME_EXCEPTION,
            -> throw ApiException(response.status)

            else -> {}
        }

        return response.data!!
    }
}