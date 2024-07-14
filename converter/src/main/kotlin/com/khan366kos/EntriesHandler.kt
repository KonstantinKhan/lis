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
                catalogs = catalogs()
            )
        }.distinct()

    private fun catalogs(): List<Catalog> = entries
        .map {
            Catalog(
                catalogName = it.catalog,
                catalogId = it.catalogId,
                groups = firstLevelGroups()
            )
        }.distinct()

    private fun firstLevelGroups(): List<Group> = entries
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

    fun allGroupsPair() = entries.map { entry ->
        entry.groups.toList()
    }.distinct()

    fun innerGroups(parentId: String, allGroupsPair: List<List<Pair<String, String>>>): List<Group> {
        val result = allGroupsPair
            .asSequence()
            .filter { arr ->
                arr.any { pair -> pair.second == parentId }
            }.toList()
            .asSequence()
            .filter { arr -> arr.size >= arr.indexOf(arr.find { pair -> pair.second == parentId }) + 2 }
            .map { arr ->
                arr[arr.indexOf(arr.find { pair -> pair.second == parentId }) + 1]
            }.distinct()
            .filter { pair -> pair.first.isNotBlank() && pair.second.isNotBlank() }
            .map { pair ->
                Group(
                    groupName = pair.first,
                    groupId = pair.second,
                    groups = innerGroups(pair.second, allGroupsPair),
                    instances = listOf()
                )
            }.toList()
        return result
    }
}