@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "UNUSED_PARAMETER", "UNUSED_VARIABLE")
package org.jonnyzzz.demo.cache6

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.readText
import kotlin.io.path.writeText

@ExperimentalPathApi

    /**
     * Loads the data from the [storage]
     * or computes it and stores otherwise
     */

    /** kotlinx.serialization version **/
    inline fun <reified T> loadOrCompute(storage: Path, compute: () -> T) : T {
        runCatching {
            return Json.decodeFromString(storage.readText())
        }

        val data = compute()
        storage.writeText(Json.encodeToString(data))
        return data
    }



val path1 : Path get() = File("f1").canonicalFile.toPath()
val path2 : Path get() = File("f2").canonicalFile.toPath()

@ExperimentalPathApi

fun main() {

    val strings = loadOrCompute(path1) {
        listOf("@jonnyzzz", "jonnyzzz.com")
    }

    val ints = loadOrCompute(path2) {
        listOf(42)
    }

    presentResults(strings, ints)

}

fun main_before() {

    val strings = slowlyComputeStrings() // runs 5 minutes
    val ints = slowlyComputeInts()       // runs 3 minutes
    presentResults(strings, ints)

}

fun main_with_cache() {

    // our cache helper generic function
    fun <T> loadOrCompute(storage: Path, compute: () -> T) : T                {TODO()}

    val strings = loadOrCompute(path1) {
        slowlyComputeStrings() // once for 5 minutes
    }

    val ints = loadOrCompute(path2) {
        slowlyComputeInts()   // once 3 minutes
    }

    presentResults(strings, ints)

}

fun slowlyComputeInts() : List<Int> = listOf(42)
fun slowlyComputeStrings() : List<String> = listOf("jonnyzzz")

fun presentResults(s: List<String>, i: List<Int>) {

}

