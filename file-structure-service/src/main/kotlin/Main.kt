package com.khan366kos

import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries

fun main() {
    try {
        val items = Path("C:\\Users\\han\\Desktop\\Тестовый проект")
        println("items: ${items.listDirectoryEntries()}")
    } catch (e: Exception) {
       println(e)
    }
}