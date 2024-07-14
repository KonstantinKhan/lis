package com.khan366kos.com.khan366kos.excel.dsl.builders

import com.khan366kos.com.khan366kos.common.models.Path
import com.khan366kos.com.khan366kos.excel.dsl.models.ExcelDSL
import com.khan366kos.com.khan366kos.excel.wrapper.models.Workbook
import com.khan366kos.common.models.builders.Builder

@ExcelDSL
class WorkbookBuilder : Builder<Workbook> {
    var path: Path = Path.NONE

    override fun build() = Workbook(path)
}