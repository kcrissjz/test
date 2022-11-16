package com.bdpp.common.network

import com.bdpp.common.ktx.logError
import com.bdpp.common.ktx.startKtxActivity
import com.bdpp.common.network.entity.*
import com.bdpp.common.util.Tip

fun <T> ApiResponse<T>.parseData(listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    when (this) {
        is ApiSuccessResponse -> listener.onSuccess(this.response)
        is ApiSuccessTokenResponse -> listener.onSuccessToken(this.response)
        is ApiEmptyResponse -> listener.onDataEmpty()
        is ApiFailedResponse -> listener.onFailed(this.code, this.msg)
        is ApiErrorResponse -> listener.onError(this.throwable)
    }
    listener.onComplete()
}

class ResultBuilder<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onSuccessToken: (token: String) -> Unit = {}
    var onDataEmpty: () -> Unit = {}
    var onFailed: (errorCode: Int?, errorMsg: String?) -> Unit = { _, errorMsg ->
        errorMsg?.let { logError("onFailed-->$it")  }
    }
    var onError: (e: Throwable) -> Unit = { e ->
        e.message?.let { logError("onError-->$it")  }
    }
    var onComplete: () -> Unit = {}
}