package com.khan366kos.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Entry(
    val reference: String,
    val catalog: String,
    val catalogId: String,
    val instance: String,
    val instanceId: String,
    val isApproved: Boolean,
    val groups: Map<String, String>,
)