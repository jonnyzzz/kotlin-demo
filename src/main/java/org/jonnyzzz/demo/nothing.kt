@file:Suppress("unused", "UNUSED_VARIABLE", "ThrowableNotThrown")

package org.jonnyzzz.demo

import kotlin.system.exitProcess


  fun ourSpecialHardStopFunction(): Nothing {
    throw Exception("assert failed")
  }

  fun example() {
    ourSpecialHardStopFunction()
    println("It will not run")
  }




@Suppress("UNREACHABLE_CODE")
fun returns(): String {

  val a = return "@jonnyzzz"
  val b = throw Exception("...")
  val c = TODO("Not ready yet")
  val d = ourSpecialHardStopFunction()
  val e = exitProcess(1)

  /// a,b,c,d,e type is Nothing

}