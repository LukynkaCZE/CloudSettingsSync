package cz.lukynka.cloudsettingssync.server

import cz.lukynka.cloudsettingssync.common.ApiResponse
import cz.lukynka.cloudsettingssync.common.ApiResponseStatus
import cz.lukynka.cloudsettingssync.common.VoidApiResponse
import cz.lukynka.cloudsettingssync.common.toJson
import cz.lukynka.lkws.responses.Response

fun Response.respondEmptySuccess() {
    this.respondJson(toJson<ApiResponse<VoidApiResponse>>(ApiResponse(ApiResponseStatus.SUCCESS, null)))
}

inline fun <reified T> Response.respondSerialized(obj: T, status: ApiResponseStatus = ApiResponseStatus.SUCCESS) {
    this.respondJson(toJson<ApiResponse<T>>(ApiResponse(status, obj)))
}

fun Response.respondError(apiResponseStatus: ApiResponseStatus, customMessage: String? = null) {
    this.respondJson(toJson<ApiResponse<VoidApiResponse>>(ApiResponse(apiResponseStatus, null)))
}