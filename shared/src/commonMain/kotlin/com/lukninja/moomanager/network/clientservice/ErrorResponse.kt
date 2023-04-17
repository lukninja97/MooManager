package com.lukninja.moomanager.network.clientservice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("error")
    val error: String? = null,
    @SerialName("error_class")
    val errorClass: String? = null,
    @SerialName("error_code")
    val errorCode: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("path")
    val path: String? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("timestamp")
    val timestamp: Double? = null
)