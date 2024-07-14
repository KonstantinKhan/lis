package com.khan366kos.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Catalog(
    val catalogName: String,
    val catalogId: String,
    val groups: List<Group>
)
