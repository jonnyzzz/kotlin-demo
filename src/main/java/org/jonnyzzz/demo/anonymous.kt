@file:Suppress("PackageDirectoryMismatch", "unused")

package org.jonnyzzz.demo.anonymous


  interface A
  interface B

  private val impl = object : A, B, Runnable {
    var state = 42

    override fun run() {
      state++
    }
  }

  //accessing members of an anonymous object
  val state = impl.state


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




