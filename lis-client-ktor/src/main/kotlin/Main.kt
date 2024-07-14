package com.khan366kos

import com.khan366kos.com.khan366kos.lis.client.ktor.client.Client
import com.khan366kos.com.khan366kos.lis.client.ktor.ConnectConfiguration
import com.khan366kos.com.khan366kos.lis.client.ktor.models.CheckInModel
import com.khan366kos.com.khan366kos.lis.client.ktor.models.InsertObjectModel
import com.khan366kos.com.khan366kos.lis.client.ktor.models.LoodsmanObjectModel
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

fun main(): Unit = runBlocking {

    // Configuration
    val connectConfiguration = ConnectConfiguration(
        dbName = "loodsmandb",
        userName = "Администратор",
        password = "Администратор",
        rememberMe = false,
        url = "http://localhost:8076/api/"
    )
    val client = Client(
        connectConfiguration
    )

    val project = Path("C:\\Users\\han\\Desktop\\Данные для тестов\\Тестовый проект")

    launch(Dispatchers.IO) {
        val loginStatus = client.login()
        println("loginStatus: ${loginStatus.bodyAsText()}")
        val sessionId = loginStatus.bodyAsText()
            .replace("{", "")
            .replace("}", "")
            .split(",")
            .first()
            .replace("\"", "")
            .split(":")[1]

        val requestProject = client.create(
            sessionId,
            LoodsmanObjectModel(
                typeName = "Папка", stateName = "Папка для чтения", keyAttribute = project.name, isProject = true
            )
        )

        val checkOutResponse = client.chekout(
            sessionId
        ) {
            url {
                parameters.append("typeName", "Папка")
                parameters.append("productName", project.name)
                parameters.append("mode", "0")
            }
        }

        val checkout = checkOutResponse.bodyAsText().replace("\"", "")

        val connectToCheckout = client.connectToCheckout(sessionId) {
            url {
                parameters.append("checkOutName", checkout)
                parameters.append("dbName", connectConfiguration.dbName)
            }
        }

        println("connectToCheckout: ${connectToCheckout.bodyAsText()}")

        project.listDirectoryEntries().forEachIndexed { index, it ->
            client.create(
                sessionId,
                LoodsmanObjectModel(
                    typeName = "Сборочная единица",
                    stateName = "Проектирование",
                    keyAttribute = it.name,
                    isProject = false
                )
            )
        }

        project.listDirectoryEntries().forEach {
            val res = client.insertObject(sessionId) {
                setBody(
                    InsertObjectModel(
                        parentType = "Папка",
                        parentProduct = project.name,
                        typeName = "Сборочная единица",
                        productName = it.name,
                        versionNumber = "1.0",
                        link = "Состоит из ...",
                        keyInsert = true
                    )
                )
            }
            println("assembly: ${res.bodyAsText()}")
        }

        client.checkIn(sessionId) {
            setBody(CheckInModel(checkOutName = checkout, dbName = connectConfiguration.dbName))
        }
    }
}