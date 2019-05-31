@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName")

package org.jonnyzzz.demo.anonymous


  interface A
  interface B
  private val impl = object : A, B, Runnable {
    var state = 42
    fun `another member`() {}

    override fun run() {
      state++
    }
  }
  //accessing members of an anonymous object
  val a = impl.state
  val b = impl.`another member`()


  interface X {
    fun bark() {}
  }
  interface Y {
    fun bark() = Unit
  }

  object Impl : X, Y {
    //resolves ambiguity
    override fun bark() {
      super<X>.bark()
      super<Y>.bark()
    }
  }




