package com.khan366kos

import com.khan366kos.com.khan366kos.common.models.Path
import com.khan366kos.com.khan366kos.excel.dsl.models.workbook
import com.khan366kos.common.models.*
import com.khan366kos.common.models.dsl.reference
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun main() {
//    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Болт ГОСТ 15163-78.xlsx"
//    val path = "C:\\Users\\han\\Desktop\\Болт откидной ГОСТ 3033-79 (исп 1В).xlsx"
//    val path = "C:\\Users\\han\\Desktop\\Болты откидные.xlsx"
    val path = "C:\\Users\\han\\Desktop\\Гайки.xlsx"
    val workbook = workbook {
        this.path = Path(path)
    }
    val entries = workbook.entries(0, 0)

    val groupList = entries.map { entry -> entry.groups.toList() }.distinct()
//    val uniqueGroups = groupList.flatten().filter { it.first.isNotBlank() && it.second.isNotBlank() }.distinct()
    val uniqueGroups = groupList.map { it.first() }.distinct()
//    println(uniqueGroups)
//        .flatten().filter { it.first.isNotBlank() && it.second.isNotBlank() }.distinct()

//    groupList.forEach { println(it) }
//    uniqueGroups.forEach { println(it) }

    fun innerGroups(parentId: String, allGroups: List<List<Pair<String, String>>>): List<Group> {
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
                    groups = innerGroups(pair.second, groupList),
                    instances = listOf()
                )
            }
            .toList()
        return result
    }

    val groups: List<Group> = uniqueGroups.map { pair ->
        Group(
            groupName = pair.first,
            groupId = pair.second,
            groups = innerGroups(pair.second, groupList),
            instances = listOf()
        )
    }

//    val groups = Group(
//        groupName = uniqueGroups[4].first,
//        groupId = uniqueGroups[4].second,
//        groups = innerGroups(uniqueGroups[4].second, groupList),
//        instances = listOf()
//    )

//    groups.forEach { group -> println(group) }


//    groups.forEach { group -> println(group) }

    val result = with(entries) {
        reference {
            referenceName = toReferenceName()
            catalogs = toCatalogs()
        }
    }

    val json = Json { prettyPrint = true }

//    println(json.encodeToString(result))

    File("file.json").printWriter()
        .use { out ->
            out.println(json.encodeToString(result))
        }
}
