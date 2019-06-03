@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.proxy2

import java.lang.reflect.Proxy


interface Service {
  fun doTheJob(): String
}

class Wrapper(x: Service) : Service by x

fun main() {

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

}

