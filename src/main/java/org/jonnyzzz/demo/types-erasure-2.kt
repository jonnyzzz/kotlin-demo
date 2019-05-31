@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.erasure2


fun main() {
  fun log(x: Any) {
    println("$x (${x::class})")
  }

  println("1 of 2")
  log(castToT<Int>("not int"))
  println("2 of 2")
  log(castToY<Int>("crash"))
}


fun <T> castToT(t: Any) = t as T
inline fun <reified Y> castToY(t: Any) = t as Y