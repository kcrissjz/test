package com.bdpp.common.network.entity

import com.bdpp.common.ktx.logError
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

enum class HttpError(var code: Int, var errorMsg: String) {
    TOKEN_EXPIRE(401, "token is expired"),
    PARAMS_ERROR(500, "params is error")
    // ...... more
}

internal fun handlingApiExceptions(code: Int?, errorMsg: String?) = when (code) {
    HttpError.TOKEN_EXPIRE.code ->  logError(HttpError.TOKEN_EXPIRE.errorMsg)
    HttpError.PARAMS_ERROR.code -> logError(HttpError.PARAMS_ERROR.errorMsg)
    else -> errorMsg?.let {logError(it)}
}

internal fun handlingExceptions(e: Throwable) = when (e) {
    is HttpException -> logError(e.message())

    is CancellationException -> {
    }
    is SocketTimeoutException -> {
    }
    is JsonParseException -> {
    }
    else -> {
    }
}