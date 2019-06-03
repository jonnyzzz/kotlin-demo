@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.collectionsHacks

fun hack(x: List<String>) {

  (x as MutableList<Any>).add(123)

}

fun main() {

  val x = listOf("123", "123", "123") + listOf("123")
  hack(x)
  println(x)

  for (v in x) {
    println(v)
  }
}

fun println(x: String) {
  println(x as Any)
}
