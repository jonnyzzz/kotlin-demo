@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.processStrings

import java.util.stream.Collectors


private const val prefix = "prefix"


private fun main2(args: List<String>): List<String>? {

    return (

            args.stream()
                .filter { it.startsWith(prefix) }
                .map { it.substring(prefix.length) }
                .collect(Collectors.toList())


            )
}

private fun main3(args: List<String>): List<String> {

    return (

            args
                .filter { it.startsWith(prefix) }
                .map { it.substring(prefix.length) }


            )
}


fun main4(args: List<String>): List<String> {
            return (

            args.mapNotNull {
                if (it.startsWith(prefix)) {
                    it.substring(prefix.length)
                } else {
                    null
                }
            }

            )
}


fun main5(args: List<String>): List<String> {

    fun String.removePrefixOrNull(prefix: String) : String? = when {
        startsWith(prefix) -> substring(prefix.length)
        else -> null
    }


    return args.mapNotNull { it.removePrefixOrNull(prefix) }
}

fun main6(args: List<String>): List<String> {

    fun String.removePrefixOrNull(prefix: String) : String? = when {
        startsWith(prefix) -> substring(prefix.length)
        else -> null
    }

    return args.mapNotNull { it.removePrefixOrNull(prefix) }
}
