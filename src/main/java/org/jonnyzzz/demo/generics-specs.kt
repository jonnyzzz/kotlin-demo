@file:Suppress("unused", "UNUSED_VARIABLE", "PackageDirectoryMismatch")
package org.jonnyzzz.demo.specializedGenerics


fun <T: Number> computePowerOf(number: T): T = TODO()
fun <T: Comparable<T>> computePowerOf(number: T): T = TODO()


fun computePowerOf(number: Double): Double = TODO()


