package com.khan366kos.common.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Instance(
    @SerialName("instance_name")
    val name: String,
    @SerialName("instance_id")
    val id: String,
    @SerialName("isApproved")
    val isApproved: Boolean,
//    @SerialName("group")
//    val group: String,
//    @SerialName("group_id")
//    val groupId: String,
)
