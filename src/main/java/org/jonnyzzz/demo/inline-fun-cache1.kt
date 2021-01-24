@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "UNUSED_PARAMETER", "UNUSED_VARIABLE")
package org.jonnyzzz.demo.cache1

import java.io.File
import java.nio.file.Path


    /**
     * Loads the data from the [storage]
     * or computes it and stores otherwise
     */
    fun <T> loadOrCompute(storage: Path, compute: () -> T) : T


{
    TODO("Implement me")
}

val path1 : Path get() = File("f1").canonicalFile.toPath()
val path2 : Path get() = File("f2").canonicalFile.toPath()

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

