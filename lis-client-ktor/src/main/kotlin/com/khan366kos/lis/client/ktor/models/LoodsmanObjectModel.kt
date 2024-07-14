package com.khan366kos.com.khan366kos.lis.client.ktor.models

import kotlinx.serialization.Serializable

@Serializable
data class LoodsmanObjectModel(
    val typeName: String,
    val stateName: String,
    val keyAttribute: String,
    val isProject: Boolean
)