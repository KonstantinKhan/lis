package com.khan366kos.com.khan366kos.lis.client.ktor.models

import kotlinx.serialization.Serializable

@Serializable
data class CheckInModel(
    val checkOutName: String,
    val dbName: String
)
