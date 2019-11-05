@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.fibonacci

fun fibonacci() = sequence {
  var prev = 1
  var cur = 1

  // this sequence is infinite
  while(true) {
    val next = prev + cur
    prev = cur
    cur = next

    yield(prev)
  }
}

fun main() {
  println(fibonacci().take(10).toList())
}
