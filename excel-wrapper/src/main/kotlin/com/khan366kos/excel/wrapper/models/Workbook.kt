package com.khan366kos.com.khan366kos.excel.wrapper.models

import com.khan366kos.com.khan366kos.common.models.Path
import com.khan366kos.common.models.Entry
import com.khan366kos.common.models.EntryHeaders
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

class Workbook(path: Path) {
    private val workbook: XSSFWorkbook = FileInputStream(path.value)
        .use { inputStream ->
            XSSFWorkbook(inputStream)
        }

    fun columnValues(columnIndex: Int) {
        workbook.getSheetAt(0).forEach { row ->
            println(row.getCell(columnIndex))
        }
    }

    fun headers(sheetIndex: Int, rowIndex: Int, columnIndex: List<Int>): List<String> =
        workbook.getSheetAt(sheetIndex)
            .getRow(rowIndex)
            .filter { columnIndex.contains(it.columnIndex) }
            .map {
                it.toString()
            }

    fun values(sheetIndex: Int, headerIndex: Int, columnIndex: List<Int>): List<List<String>> =
        workbook.getSheetAt(sheetIndex)
            .filter { it.rowNum != headerIndex }
            .map { row ->
                row.filter { columnIndex.contains(it.columnIndex) }.map { it.toString() }
            }

    fun groupsCount(sheetIndex: Int, rowIndex: Int): Int = workbook
        .getSheetAt(sheetIndex)
        .getRow(rowIndex).count {
            println(it)
            it.toString() == "GROUPS"
        }

    private fun entryHeaders(sheetIndex: Int, rowIndex: Int): EntryHeaders {
        val arr = workbook.getSheetAt(sheetIndex).getRow(rowIndex).map { it }
        val catalogs =
            arr.filter { it.toString() == "GROUPS" || it.toString() == "IDEXTERNAL_GROUPS" }.map { it.columnIndex }
        return EntryHeaders(
            reference = arr.first { it.toString() == "REFERENCE" }.columnIndex,
            catalog = arr.first { it.toString() == "CATALOGS" }.columnIndex,
            catalogId = arr.first { it.toString() == "IDEXTERNAL_CATALOGS" }.columnIndex,
            instance = arr.first { it.toString() == "NAME" }.columnIndex,
            instanceId = arr.first { it.toString() == "IDEXTERNAL_OBJ" }.columnIndex,
            isApproved = try {
                arr.first { it.toString() == "PROPERTY_f5bdf06e-6ac8-4748-b07a-c16819967073" }.columnIndex
            } catch (e: Exception) {
                1000
            },
            catalogs = catalogs
                .map { i -> catalogs.map { i to it } }
                .flatten()
                .filter { (left, right) -> left != right && left - right == -1 }.toList().toMap()
        )
    }

    fun entries(sheetIndex: Int, headerIndex: Int): List<Entry> {
        val entryHeaders = entryHeaders(sheetIndex, headerIndex)
        return workbook
            .getSheetAt(sheetIndex)
            .filter { it.rowNum != headerIndex
//                    && it.getCell(entryHeaders.instance).toString().isNotBlank()
            }
            .map { row ->
                Entry(
                    reference = row.getCell(entryHeaders.reference).toString(),
                    catalog = row.getCell(entryHeaders.catalog).toString(),
                    catalogId = row.getCell(entryHeaders.catalogId).toString(),
                    instance = row.getCell(entryHeaders.instance).toString(),
                    instanceId = row.getCell(entryHeaders.instanceId).toString(),
                    isApproved = try { row.getCell(entryHeaders.isApproved).toString() == "Разрешен к применению" } catch (e: Exception) { false},
                    groups = entryHeaders.catalogs.map { (k, v) ->
                        row.getCell(v).toString() to row.getCell(k).toString()
                    }.filter {
                        it.first.isNotBlank() && it.second.isNotBlank()
                    }.toMap()
                )
            }
    }
}