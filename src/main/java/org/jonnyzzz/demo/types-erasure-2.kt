@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.erasure2

  inline fun <reified Y> reifiedCast(t: Any) = t as Y
  fun <T> hackCast(t: Any) = t as T

  val test = run {
    hackCast<Int>("not int")

    reifiedCast<Int>("crash")
  }

  fun log(x: Any) {
    println("$x (${x::class})")
  }


fun main() {
  println(test)
}

private inline fun run(a: () -> Unit) = a()