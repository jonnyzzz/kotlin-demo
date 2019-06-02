@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.erasure2


  fun <T> castToT(t: Any) = t as T


  inline fun <reified Y> castToY(t: Any) = t as Y

  val test = run {
    log(castToT<Int>("not int"))
    log(castToY<Int>("crash"))
  }

  fun log(x: Any) {
    println("$x (${x::class})")
  }


fun main() {
  println(test)
}

