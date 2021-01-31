@file:Suppress("PackageDirectoryMismatch", "unused")
package org.jonnyzzz.demo.inlinefun


fun example1() {

    /// option 1
    listOf(1, 2, 3, 4).map { it * it }

    /// option 2
    listOf(1, 2, 3, 4).asSequence().map { it * it }

}


fun exampleInf() {
    val seq = sequence<Int> {
        while(true) { (1..100).random() }
    }

    /// option 1
    seq.map { it * it }

    /// option 2
    seq.asSequence().map { it * it }
}

fun main() {
    exampleInf()
}
