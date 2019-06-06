@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.proxy2

import java.lang.reflect.Proxy


interface Service {
  fun doTheJob(): String
}

class Wrapper(x: Service) : Service by x

fun main() {
  val y = (JService() as Service).doTheJob()

  y == null


  println("The value == null is ${y == null}")
  println("The value is $y")


  val clazz = Service::class.java
  val service = Proxy.newProxyInstance(
          clazz.classLoader,
          arrayOf(clazz)) { _, _, _ ->
    //returns null from the proxy
    null
  } as Service

  val x = service.doTheJob()

  x == null


  println("The value == null is ${x == null}")
  println("The value is $x")

  callWithFakeNullPrivate(x)
  callWithFakeNull(x)

}

fun callWithFakeNull(x: String) {
  println("I'm ok: $x")
}

private fun callWithFakeNullPrivate(x: String) {
  println("I'm ok: $x")
}
