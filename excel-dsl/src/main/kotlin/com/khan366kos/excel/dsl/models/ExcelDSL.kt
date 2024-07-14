package com.khan366kos.com.khan366kos.excel.dsl.models

import com.khan366kos.com.khan366kos.excel.dsl.builders.WorkbookBuilder
import com.khan366kos.com.khan366kos.excel.wrapper.models.Workbook

fun workbook(block: WorkbookBuilder.() -> Unit): Workbook = WorkbookBuilder().apply(block).build()