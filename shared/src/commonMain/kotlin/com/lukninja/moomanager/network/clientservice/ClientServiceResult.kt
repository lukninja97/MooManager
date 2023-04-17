package com.lukninja.moomanager.network.clientservice

sealed class ClientServiceResult<out T> {
    data class Success<out T>(val result: T) : ClientServiceResult<T>()
    data class Fail(val message: String = "", val error: ErrorResponse? = null) :
        ClientServiceResult<Nothing>()
}
    