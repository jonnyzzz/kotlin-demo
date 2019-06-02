@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.proxy1

import java.lang.reflect.Proxy


interface Service {
  fun doTheJob(): String
}

class ServiceException : Exception()

fun main() {

  val clazz = Service::class.java
  val service = Proxy.newProxyInstance(
          clazz.classLoader,
          arrayOf(clazz)) { _, _, _ ->
    //throw exception from the proxy
    throw ServiceException()
  } as Service

  try {
    service.doTheJob()
  } catch (e: ServiceException) {
    //[dog] It's fine!
  }
}


