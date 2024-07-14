package com.khan366kos.com.khan366kos.common.models

@JvmInline
value class Path(private val path: String) {
    val value: String
        get() = path

    companion object {
        val NONE = Path(String())
    }
}