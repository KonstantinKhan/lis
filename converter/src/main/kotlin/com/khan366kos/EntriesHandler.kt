package com.khan366kos.com.khan366kos

import com.khan366kos.common.models.*

class EntriesHandler(private val entries: List<Entry>) {

    fun references(): List<Reference> = entries
        .map { entry ->
            entry.reference
        }.distinct()
        .map { reference ->
            Reference(
                referenceName = reference,
                catalogs = catalogs(reference)
            )
        }.distinct()

    private fun catalogs(reference: String): List<Catalog> = entries
        .filter { entry ->
            entry.reference == reference
        }.distinct()
        .map {
            Catalog(
                catalogName = it.catalog,
                catalogId = it.catalogId,
                groups = firstLevelGroups(it.catalogId)
            )
        }.distinct()

    private val allGroupsPair: List<List<Pair<String, String>>> by lazy {
        entries.map { entry ->
            entry.groups.toList()
        }.distinct()
    }

    private fun firstLevelGroups(catalogId: String): List<Group> =
        entries
            .asSequence()
            .filter { entry ->
                entry.catalogId == catalogId
            }.distinct()
            .map { entry ->
                entry.groups.toList().first()
            }
            .distinct()
            .map {
                with(it) {
                    Group(
                        groupName = second,
                        groupId = first,
                        groups = innerGroups(first, allGroupsPair),
                        instances = instances(first)
                    )
                }
            }.distinct()
            .toList()

    private fun innerGroups(parentId: String, allGroupsPair: List<List<Pair<String, String>>>): List<Group> {
        val result = allGroupsPair
            .asSequence()
            .filter { arr ->
                arr.any { pair ->
                    pair.first == parentId
                }
            }.toList()
            .asSequence()
            .filter { arr ->
                arr.size >= arr.indexOf(arr.find { pair -> pair.first == parentId }) + 2
            }
            .map { arr ->
                arr[arr.indexOf(arr.find { pair -> pair.first == parentId }) + 1]
            }.distinct()
            .filter { pair -> pair.first.isNotBlank() && pair.second.isNotBlank() }
            .map { pair ->
                Group(
                    groupName = pair.second,
                    groupId = pair.first,
                    groups = innerGroups(pair.first, allGroupsPair),
                    instances = instances(pair.first)
                )
            }.toList()
        return result
    }

    private fun instances(groupId: String): List<Instance> = entries
        .filter { entry: Entry ->
            entry.groups.toList()
                .last().first == groupId
        }.map {
            if (it.instance.isNotBlank())
                Instance(
                    name = it.instance,
                    id = it.instanceId,
                    isApproved = it.isApproved
                )
            else return emptyList()
        }
}