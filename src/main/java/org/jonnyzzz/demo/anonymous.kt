@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.anonymous


  interface A
  interface B

  private val impl = object : A, B, Runnable {
    var state = 42

    override fun run() {
      state++
    }
  }

  //accessing fields of an anonymous object
  val state = impl.state


val mock = state

