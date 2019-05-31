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

  private val mix = object : X, Y {
    override fun bark() {
      super<X>.bark() //call X.bark()
      super<Y>.bark() //call Y.bark()
    }
  }




