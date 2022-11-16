package com.bdpp.common.network.entity

import java.io.Serializable

open class ApiResponse<T>(
        open val data: T? = null,
        open var token: String = "",
        open val code: Int? = null,
        open val msg: String? = null,
        open val error: Throwable? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = code == 0
    val isTokenDis:Boolean
        get() = code == 401

    override fun toString(): String {
        return "ApiResponse(data=$data, errorCode=$code, errorMsg=$msg, error=$error)"
    }


}

data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(data = response)
data class ApiSuccessTokenResponse<T>(val response: String) : ApiResponse<T>(token = response)

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(override val code: Int?, override val msg: String?) : ApiResponse<T>(code = code, msg = msg)

data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)
