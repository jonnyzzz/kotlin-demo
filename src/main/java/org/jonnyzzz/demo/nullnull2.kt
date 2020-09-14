@file:Suppress("PackageDirectoryMismatch", "DeprecatedCallableAddReplaceWith", "UNUSED_PARAMETER")
package org.jonnyzzz.demo.nullnull2

import kotlin.DeprecationLevel.ERROR


fun main() {

//  @Deprecated("fix me", level = ERROR)
  operator fun Nothing?.plus(x: Any?): Unit = TODO()

  val x = null + null

  // what will it print?
  println(x::class)
  println(x)
}

fun inner() {
  val x = null.plus(null)

  // what will it print?
  println(x::class)
  println(x)



}

/*
  /**
   * Concatenates this string with the
   * string representation of the given
   * [other] object. If either the receiver
   * or the [other] object are null, they are
   * represented as the string "null".
   */
  operator fun String?.plus(other: Any?): String


= TODO(other.toString())

**/