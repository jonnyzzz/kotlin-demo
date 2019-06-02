@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.proxy2

import java.lang.reflect.Proxy


interface Service {
  fun doTheJob(): String
}

fun main() {
  val clazz = Service::class.java
  val service = Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz)) { _, m, _ ->
    if (m.name == Service::doTheJob.name) {
      return@newProxyInstance null
    }
    Any()
  } as Service

  val x = service.doTheJob()

  x == null
  println("Actual value is ${x == null}")
  println("Actual value is $x")
}

