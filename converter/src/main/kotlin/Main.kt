package com.khan366kos

import com.khan366kos.com.khan366kos.EntriesHandler
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
//    val path = "C:\\Users\\han\\Desktop\\Гайки.xlsx"
//    val path = "/home/khan/Загрузки/Telegram Desktop/Болт ГОСТ 15163-78.xlsx"
//    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Тест выгрузка\\Кабель КГЭШ 3х10+1х6+3х2,5-1140 ТУ 16.К73.012-95.xlsx"
//    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Тест выгрузка\\Конденсатор К10-17а-50В-1.2мкФ-±10%-МП0 ОЖ0.460.107ТУ.xlsx"
//    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Тест выгрузка\\Испытания.xlsx"
//    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Тест выгрузка\\Зажим с двумя болтами ГОСТ 21130-75.xlsx"
    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Тест выгрузка\\Болт DIN 797.xlsx"
//    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Тест выгрузка\\Гвозди.xlsx"
//    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Тест выгрузка\\Винт ГОСТ 10336-80 (исп 1).xlsx"


    val workbook = workbook {
        this.path = Path(path)
    }
    val entries = workbook.entries(0, 0)

    entries.forEach { entry -> println(entry) }

    val handler = EntriesHandler(entries)

    val json = Json { prettyPrint = true }

    File("file.json").printWriter()
        .use { out ->
            out.println(json.encodeToString(handler.references()))
        }

//    val groupList = entries.map { entry -> entry.groups.toList() }.distinct()
//    val uniqueGroups = groupList.map { it.first() }.distinct()
//
//    fun innerGroups(parentId: String, allGroups: List<List<Pair<String, String>>>): List<Group> {
//        val result = allGroups
//            .asSequence()
//            .filter { a ->
//                a.any { b -> b.second == parentId }
//            }
//            .toList()
//            .asSequence()
//            .filter { a -> a.size >= a.indexOf(a.find { pair -> pair.second == parentId }) + 2 }
//            .map { a ->
//                a[a.indexOf(a.find { pair -> pair.second == parentId }) + 1]
//            }
//            .distinct().filter { a -> a.first.isNotBlank() && a.second.isNotBlank() }
//            .map { pair ->
//                Group(
//                    groupName = pair.first,
//                    groupId = pair.second,
//                    groups = innerGroups(pair.second, groupList),
//                    instances = listOf()
//                )
//            }
//            .toList()
//        return result
//    }
//
//    val groups: List<Group> = uniqueGroups.map { pair ->
//        Group(
//            groupName = pair.first,
//            groupId = pair.second,
//            groups = innerGroups(pair.second, groupList),
//            instances = listOf()
//        )
//    }
// ----------------------------------
//    val result = with(entries) {
//        reference {
//            referenceName = toReferenceName()
//            catalogs = toCatalogs()
//        }
//    }
//
//    val json = Json { prettyPrint = true }
//
//    File("file.json").printWriter()
//        .use { out ->
//            out.println(json.encodeToString(result))
//        }
}

