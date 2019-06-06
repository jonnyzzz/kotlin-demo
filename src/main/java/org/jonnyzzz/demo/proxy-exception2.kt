@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.proxy4

interface Service {
  fun doTheJob(): String
}

class ServiceException : Exception()

class Proxy {
  companion object {
    fun newProxyInstance(): Any = TODO()
  }
}

fun main2() {


  val clazz = Service::class.java

  val service = Proxy.newProxyInstance(/*...*/) as Service

  try {
    service.doTheJob()
  } catch (e: ServiceException) {
    //It's fine!
  }

}



