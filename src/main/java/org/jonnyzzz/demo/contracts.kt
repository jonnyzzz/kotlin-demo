@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.contracts

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun doTheWork(x: () -> Unit) {
  contract {
    callsInPlace(x, InvocationKind.EXACTLY_ONCE)
  }
  x()
}


fun main() {
  val value : Int
  doTheWork {
    value = 25
  }

  println(value)
}