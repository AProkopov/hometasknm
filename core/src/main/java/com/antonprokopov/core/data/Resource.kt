package com.antonprokopov.core.data

sealed class Resource<T> {

    companion object {
        fun <T> newLoading(): Resource<T> = Loading(null)
        fun <T> newSuccess(data: T): Resource<T> = Success(data)
        fun <T> newError(data: T?, errorDesc: ErrorDesc): Resource<T> = Error(data, errorDesc)
        fun <T> newError(data: T?, message: String? = null): Resource<T> = newError(data, ErrorDesc(message = message))
        fun <T> newError(message: String? = null): Resource<T> = newError(null, message)
    }

    abstract val data: T?

    data class Loading<T> internal constructor(override val data: T?) : Resource<T>()
    data class Error<T> internal constructor(override val data: T?, val desc: ErrorDesc) : Resource<T>()
    data class Success<T> internal constructor(override val data: T) : Resource<T>()

    data class ErrorDesc(
        val message: String? = null,
        val messageId: Int? = null,
        val errorCode: Int? = null,
        val isNetwork: Boolean? = false
    )
}
