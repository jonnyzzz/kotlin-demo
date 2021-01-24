@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "DuplicatedCode", "ObjectLiteralToLambda"
)
package org.jonnyzzz.demo.typeOf

import kotlin.reflect.typeOf

@ExperimentalStdlibApi
fun main() {

    val type1 = typeOf<Map<String, List<String>>>()

    println(type1)

}




