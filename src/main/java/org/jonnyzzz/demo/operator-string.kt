@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.ops

fun main() {
  operator fun Nothing?.plus(x: Any?): Nothing = TODO()

  val z: String? = null
  println(z + "234" + "1234")


  val x = null + null

  // class kotlin.String
  println(x::class.java.genericSuperclass)
  // nullnull
  //println(x)

}

