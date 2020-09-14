@file:Suppress("unused", "UNUSED_VARIABLE")

package org.jonnyzzz.demo


  fun theAssert(): Nothing {
    throw Exception("assert failed")
  }

  fun example() {
    theAssert()
    println("It will not run")
  }


@Suppress("UNREACHABLE_CODE")
fun returns(): String {

  val a = return "@jonnyzzz"
  val b = throw Exception("...")
  val c = TODO("Not ready yet")
  val d = theAssert()

  /// a,b,c,d type is Nothing

}