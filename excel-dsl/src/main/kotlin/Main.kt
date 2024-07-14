package com.khan366kos

import com.khan366kos.com.khan366kos.common.models.Path
import com.khan366kos.com.khan366kos.excel.dsl.models.workbook

fun main() {
    val path = "C:\\Users\\han\\Downloads\\Telegram Desktop\\Болт ГОСТ 15163-78.xlsx"
    val workbook = workbook {
        this.path = Path(path)
    }
    println("workbook = $workbook")
}