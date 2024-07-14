package com.khan366kos.com.khan366kos.lis.client.ktor.models

import kotlinx.serialization.Serializable

@Serializable
data class InsertObjectModel(
    val parentType: String,
    val parentProduct: String,
    val parentVersion: String? = null,
    val typeName: String,
    val productName: String,
    val versionNumber: String,
    val link: String,
    val stateName: String? = null,
    val keyInsert: Boolean
)
