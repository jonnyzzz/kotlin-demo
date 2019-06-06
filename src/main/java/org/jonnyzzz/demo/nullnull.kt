@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.nullnull


operator fun Nothing?.plus(x: Any?): Nothing = TODO()

fun main() {


  val y = "null" + null
  val x = null + null

  println(y)

}
