package com.khan366kos.com.khan366kos

import com.khan366kos.common.models.Catalog
import com.khan366kos.common.models.Entry
import com.khan366kos.common.models.Group
import com.khan366kos.common.models.Reference

class EntriesHandler(private val entries: List<Entry>) {

    fun references(): List<Reference> = entries
        .map {
            Reference(
                referenceName = it.reference,
                catalogs = listOf()
            )
        }.distinct()

    fun catalogs(): List<Catalog> = entries
        .map {
            Catalog(
                catalogName = it.catalog,
                catalogId = it.catalogId,
                groups = listOf()
            )
        }.distinct()

    fun firstLevelGroups(): List<Group> = entries
        .map {
            with(it.groups.toList().first()) {
                Group(
                    groupName = first,
                    groupId = second,
                    groups = listOf(),
                    instances = listOf()
                )
            }
        }.distinct()
}