package com.khan366kos.com.khan366kos.lis.client.ktor.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val dbName: String,
    val username: String,
    val password: String,
    val rememberMe: Boolean = false
)
