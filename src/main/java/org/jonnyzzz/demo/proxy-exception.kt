@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.proxy1

import java.lang.reflect.Proxy


interface Service {
  fun doTheJob(): String
}


fun main() {
  val clazz = Service::class.java
  val service = Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz)) { _, m, _ ->
    if (m.name == Service::doTheJob.name) {
      throw Exception("Not yet ready!")
    }
  } as Service

  service.doTheJob()
}
