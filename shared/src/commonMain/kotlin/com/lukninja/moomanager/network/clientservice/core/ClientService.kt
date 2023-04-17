package com.lukninja.moomanager.network.clientservice.core

import co.touchlab.kermit.Logger
import com.lukninja.moomanager.network.clientservice.ClientServiceResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class ClientService {
    val client = HttpClient {

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
        }

        install(ContentNegotiation) {
            json(Json {
                useAlternativeNames = false
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    Logger.d { "ClientService -> $message " }
                }
            }
        }
    }

    suspend inline fun <T, reified E> safeResponse(
        url: Url,
        body: T? = null,
        httpMethod: HttpMethod = HttpMethod.Get,
        headers: HashMap<String, String>? = null,
    ): ClientServiceResult<E> {
        return try {
            val result = client.request(url) {

                method = httpMethod
                headers?.forEach {
                    headers {
                        append(it.key, it.value)
                    }
                }
                if (httpMethod == HttpMethod.Post || httpMethod == HttpMethod.Put || httpMethod == HttpMethod.Patch) {
                    contentType(ContentType.Application.Json)
                    this.setBody(body ?: EmptyContent)
                }
            }
            return if (result.status.isSuccess()) {
                Logger.d { "ClientService Call -> $result" }
                ClientServiceResult.Success(result.body())
            } else {
                Logger.d { "ClientService Call Fail -> $result" }
                try {
                    Logger.w(Exception("$result")) { "ClientService fail api result ${result.status}" }
                    ClientServiceResult.Fail(
                        message = "HTTP Response code ${result.status}",
                    )
                } catch (e: Exception) {
                    Logger.w(e) { "ClientService fail exception result" }
                    ClientServiceResult.Fail("HTTP Response code ${result.status}")
                }
            }
        } catch (e: Exception) {
            Logger.w(e) { "ClientService fail exception call $e" }
            Logger.d { "ClientService Call Exception-> ${e.message}" }
            ClientServiceResult.Fail(e.message.toString())
        } finally {
            client.close()
        }
    }
}