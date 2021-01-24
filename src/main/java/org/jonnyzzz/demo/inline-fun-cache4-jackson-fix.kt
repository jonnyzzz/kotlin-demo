@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "DuplicatedCode"
)
package org.jonnyzzz.demo.cache4

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.network.sockets.*
import java.io.File
import java.nio.file.Path


/**
     * Loads the data from the [storage]
     * or computes it and stores otherwise
     */

    /** Jackson version **/
    fun <T> loadOrCompute(storage: File,
                          type: TypeReference<T>,
                          compute: () -> T) : T



    {






        runCatching {
            return jacksonObjectMapper().readValue(storage, type)
        }

        println("Computing...")
        val data = compute()
        jacksonObjectMapper().writeValue(storage, data)
        return data
    }



val path1 get() = File("f1").canonicalFile.toPath().toFile()
val path2 get() = File("f2").canonicalFile.toPath().toFile()
val path3 get() = File("f3").canonicalFile.toPath().toFile()
val path4 get() = File("f4").canonicalFile.toPath().toFile()


data class C1(val name: String)
data class C2(val weight: Long)

    val t1 = object: TypeReference<List<String>>(){}
    val t2 = object: TypeReference<List<Int>>(){}



fun main() {

    repeat(3) {
        val strings = loadOrCompute(path1, object: TypeReference<List<String>>(){}) {
            listOf("@jonnyzzz", "jonnyzzz.com")
        }

        val ints = loadOrCompute(path2, object: TypeReference<List<Int>>(){}) {
            listOf(42)
        }

        println("Strings: " + strings.joinToString())
        println("Ints: " + ints.joinToString())
    }

    repeat(3) {

        val list: List<C1> = loadOrCompute(path3, object: TypeReference<List<C1>>(){}) {
            listOf(C1("@jonnyzzz"), C1("jonnyzzz.com"))
        }

        val map: Map<String, Pair<C1,C2>> = loadOrCompute(path4, object: TypeReference<Map<String, Pair<C1,C2>>>(){}) {
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

