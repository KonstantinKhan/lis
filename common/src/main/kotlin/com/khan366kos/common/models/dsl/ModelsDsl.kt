package com.khan366kos.common.models.dsl

import com.khan366kos.common.models.Reference
import com.khan366kos.common.models.builders.ReferenceBuilder

fun reference(block: ReferenceBuilder.() -> Unit): Reference =
    ReferenceBuilder().apply(block).build()