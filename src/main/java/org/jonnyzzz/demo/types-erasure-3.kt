@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.erasure6

  fun <T> hackCast(t: Any): T = t as T

  inline fun <reified Y> reifiedCast(t: Any): Y = t as Y

  val test = run {
    hackCast<Int>("not int")

    reifiedCast<Int>("crash")
  }

  fun log(x: Any) {
    println("$x (${x::class})")
  }


fun main() {
  println(test)

  val strings = listOf("@jonnyzzz")
  val ints = reifiedCast<List<Int>>(strings)
  println(ints[0].javaClass)


}

private inline fun run(a: () -> Unit) = a()