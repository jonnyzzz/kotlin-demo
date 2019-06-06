@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.proxy1

import java.lang.reflect.Proxy


interface Service {
  fun doTheJob(): String
}

class ServiceException : Exception()

fun main() {

  val clazz = Service::class.java
  // implement Service implicitly
  val service = Proxy.newProxyInstance(
          clazz.classLoader,
          arrayOf(clazz)) { _, _, _ ->
    //throws exception for all methods
    throw ServiceException()
  } as Service
  //let's call the Service#doTheJob
  service.doTheJob()

}


