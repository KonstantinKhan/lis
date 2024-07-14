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

fun List<Entry>.toReferenceName() = this.first().reference

fun List<Entry>.toCatalogs() = this
    .map { entry: Entry -> Pair(entry.catalog, entry.catalogId) }
    .distinct()
    .map {
        it.toCatalog(this)
    }

private fun Pair<String, String>.toCatalog(entries: List<Entry>) = Catalog(
    catalogName = first,
    catalogId = second,
    groups = entries.toGroupsFromEntry()
)

fun List<Entry>.toGroupsFromEntry(): List<Group> {

//    val groupList = this.map { entry -> entry.groups.toList() }
    val groupList = this.map { entry -> entry.groups.toList() }.distinct()
//    val uniqueGroups = groupList.flatten().filter { it.first.isNotBlank() && it.second.isNotBlank() }.distinct()
    val uniqueGroups = groupList.map { it.first() }.distinct()

    val groups: List<Group> = uniqueGroups.map { pair ->
        Group(
            groupName = pair.first,
            groupId = pair.second,
            groups = innerGroups(pair.second, groupList, this),
            instances = emptyList()
        )
    }

//    groups.forEach { println(it) }

    return groups
}

fun innerGroups(
    parentId: String,
    allGroups: List<List<Pair<String, String>>>,
    entries: List<Entry>
): List<Group> {
    val result = allGroups
        .asSequence()
        .filter { a ->
            a.any { b -> b.second == parentId }
        }
        .toList()
        .asSequence()
        .filter { a -> a.size >= a.indexOf(a.find { pair -> pair.second == parentId }) + 2 }
        .map { a ->
            a[a.indexOf(a.find { pair -> pair.second == parentId }) + 1]
        }
        .distinct().filter { a -> a.first.isNotBlank() && a.second.isNotBlank() }
        .map { pair ->
            Group(
                groupName = pair.first,
                groupId = pair.second,
                groups = innerGroups(pair.second, allGroups, entries),
                instances = entries.toInstances(pair.second)
            )
        }
        .toList()
    return result
}

//fun List<Entry>.toGroupsFromEntry() = this
//    .asSequence()
//    .map { entry: Entry -> entry.groups }
//    .distinct().toList()
//    .map {
//        it.toList()
//    }.flatten()
//    .toList()
//    .filter { it.first.isNotBlank() }
//    .distinct().toGroups(this, this.uniqueGroupsCount() - maxOf { it.groups.size })

fun List<Entry>.uniqueGroupsCount() = this
    .asSequence().map { entry: Entry ->
        entry.groups
    }.distinct().toList().map {
        it.toList()
    }.flatten().distinct().size

fun List<Pair<String, String>>.toGroups(entries: List<Entry>, count: Int) =
    with(this) {
        println(entries.maxOf { it.groups.size })
        if (this.isEmpty()) emptyList()
        else
            if (this.size - count > 1)
                listOf(
                    this
                        .first()
                        .toGroup(
                            this.slice(1 until this.size),
                            entries
                        )
                )
            else this.toGroupsWithInstance(entries)
    }

fun Pair<String, String>.toGroup(list: List<Pair<String, String>>, entries: List<Entry>): Group =
    Group(
        groupName = this.first,
        groupId = this.second,
        groups = list.toGroups(entries, entries.uniqueGroupsCount() - entries.maxOf { it.groups.size }),
        instances = emptyList()
    )

fun List<Pair<String, String>>.toGroupsWithInstance(entries: List<Entry>) = this.map {
    Group(
        groupName = it.first,
        groupId = it.second,
        groups = emptyList(),
        instances = entries.toInstances(it.second)
    )
}

fun List<Entry>.toInstances(groupId: String): List<Instance> =
    this@toInstances
        .filter { entry: Entry ->
            entry.groups.toList()
                .last().second == groupId
        }.map {
            if (it.instance.isNotBlank())
                Instance(
                    name = it.instance,
                    id = it.instanceId,
                    isApproved = it.isApproved
                )
            else return emptyList()
        }
