@file:Suppress("PackageDirectoryMismatch", "DeprecatedCallableAddReplaceWith")
package org.jonnyzzz.demo.nullnull2



fun main() {

  //@Deprecated("fix me", level = DeprecationLevel.ERROR)
  operator fun Nothing?.plus(x: Any?): Unit = TODO()

  val x = null + null


  println(x::class)
  println(x)

}
