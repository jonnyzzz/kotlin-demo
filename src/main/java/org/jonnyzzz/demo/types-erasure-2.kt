@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.erasure3

  inline fun <reified Y> reifiedCast(t: Any) = t as Y

  val test = run {
    val strings = listOf("@jonnyzzz")
    val ints = reifiedCast<List<Int>>(strings)

    println("The result is $ints")
  }

fun main() {
  println(test)

}

private inline fun run(a: () -> Unit) = a()