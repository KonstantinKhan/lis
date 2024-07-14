package com.khan366kos.common.models

data class EntryHeaders(
    val reference: Int,
    val catalog: Int,
    val catalogId: Int,
    val instance: Int,
    val instanceId: Int,
    val isApproved: Int,
    val catalogs: Map<Int, Int>,
)