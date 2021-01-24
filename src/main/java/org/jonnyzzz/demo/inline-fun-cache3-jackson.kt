@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "UNUSED_PARAMETER", "UNUSED_VARIABLE")
package org.jonnyzzz.demo.cache3

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.Path


/**
     * Loads the data from the [storage]
     * or computes it and stores otherwise
     */

    /** Jackson version **/
    fun <T> loadOrCompute(storage: File, compute: () -> T) : T {
        run {
            return jacksonObjectMapper().readValue(storage, object : TypeReference<T>() {})
        }

        val data = compute()
        jacksonObjectMapper().writeValue(storage, data)
        return data
    }



val path1 get() = File("f1").canonicalFile.toPath().toFile()
val path2 get() = File("f2").canonicalFile.toPath().toFile()
val path3 get() = File("f3").canonicalFile.toPath().toFile()
val path4 get() = File("f4").canonicalFile.toPath().toFile()

fun main() {

    repeat(3) {
        val strings = loadOrCompute(path1) {
            listOf("@jonnyzzz", "jonnyzzz.com")
        }

        val ints = loadOrCompute(path2) {
            listOf(42)
        }

        println("Strings: " + strings.joinToString())
        println("Ints: " + ints.joinToString())
    }

    repeat(3) {

        data class C1(val name: String)
        data class C2(val weight: Long)

        val list: List<C1> = loadOrCompute(path3) {
            listOf(C1("@jonnyzzz"), C1("jonnyzzz.com"))
        }
        val map: Map<String, Pair<C1,C2>> = loadOrCompute(path4) {
            mapOf("test" to (C1("@jonnyzzz") to C2(768)))
        }

        println("list: " + list.joinToString())
        println("map: " + map.entries.joinToString())

        println("list: " + list.joinToString {
            it.javaClass.name
        })
        println("map: " + map.entries.joinToString {
            it.key.javaClass.name + " => " + it.value.javaClass.name
        })

    }


}

fun slowlyComputeInts() : List<Int> = listOf(42)
fun slowlyComputeStrings() : List<String> = listOf("jonnyzzz")

fun presentResults(s: List<String>, i: List<Int>) {
    println("Strings: " + s.joinToString())
    println("Ints: " + i.joinToString())
}

