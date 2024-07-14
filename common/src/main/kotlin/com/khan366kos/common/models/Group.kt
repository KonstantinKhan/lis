package com.khan366kos.common.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    @SerialName("group_name")
    val groupName: String,
    @SerialName("group_id")
    val groupId: String,
    @SerialName("groups")
    val groups: List<Group>,
    @SerialName("instances")
    val instances: List<Instance>
)
