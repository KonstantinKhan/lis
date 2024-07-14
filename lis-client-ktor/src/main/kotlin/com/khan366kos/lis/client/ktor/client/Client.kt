package com.khan366kos.com.khan366kos.lis.client.ktor.client

import com.khan366kos.com.khan366kos.lis.client.ktor.ConnectConfiguration
import com.khan366kos.com.khan366kos.lis.client.ktor.models.LoginModel
import com.khan366kos.com.khan366kos.lis.client.ktor.models.LoodsmanObjectModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class Client(
    private val connectConfiguration: ConnectConfiguration,
) {

    private val client: HttpClient = HttpClient(CIO) {
        defaultRequest {
            contentType(ContentType.Application.Json)
            url(connectConfiguration.url)
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }
    }

    suspend fun login(): HttpResponse = client.post("Auth/login") {
        setBody(
            with(connectConfiguration) {
                LoginModel(
                    dbName,
                    userName,
                    password,
                    rememberMe
                )
            }
        )
    }

    suspend fun user(): HttpResponse = client.get("Auth/current-user")

    suspend fun users(): HttpResponse = client.get("DbAdministrator/get-activity")

    suspend fun tree(sessionId: String): HttpResponse = client.getWithSession("Pdm/get-tree", sessionId)

    suspend fun create(sessionId: String, loodsmanObject: LoodsmanObjectModel): HttpResponse =
        client.postWithSession("EditObject/new-object", sessionId) {
            setBody(loodsmanObject)
        }

    suspend fun connectToCheckout(sessionId: String, block: HttpRequestBuilder.() -> Unit): HttpResponse =
        client.getWithSession("CheckOut/connect-to-check-out", sessionId) {
            block()
        }

    suspend fun insertObject(sessionId: String, block: HttpRequestBuilder.() -> Unit): HttpResponse =
        client.postWithSession("EditObject/insert-object", sessionId) {
            block()
        }

    suspend fun checkIn(sessionId: String, block: HttpRequestBuilder.() -> Unit): HttpResponse =
        client.postWithSession("CheckOut/check-in-2", sessionId) {
            block()
        }

    suspend fun chekout(sessionId: String, block: HttpRequestBuilder.() -> Unit): HttpResponse =
        client.getWithSession("CheckOut/check-out", sessionId) {
            block()
        }
}

