package com.khan366kos.common.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reference(
    @SerialName("reference_name")
    val referenceName: String,
    @SerialName("catalogs")
    val catalogs: List<Catalog>,
)
