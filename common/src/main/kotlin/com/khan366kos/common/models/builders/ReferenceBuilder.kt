package com.khan366kos.common.models.builders

import com.khan366kos.common.models.Catalog
import com.khan366kos.common.models.Reference
import com.khan366kos.common.models.dsl.ModelDSL

@ModelDSL
class ReferenceBuilder : Builder<Reference> {
    var referenceName: String = ""
    var catalogs: List<Catalog> = emptyList()

    override fun build() = Reference(referenceName, catalogs)
}