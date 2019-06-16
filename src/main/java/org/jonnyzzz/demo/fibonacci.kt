@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.fibonacci

fun fibonacci() = sequence {
  var a_0 = 1
  var a_1 = 1

  // this sequence is infinite
  while(true) {
    val a_n = a_0 + a_1
    a_0 = a_1
    a_1 = a_n

    yield(a_0)
  }
}

fun main() {
  println(fibonacci().take(10).toList())
}
