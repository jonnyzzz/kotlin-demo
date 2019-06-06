@file:Suppress("PackageDirectoryMismatch", "UNUSED_ANONYMOUS_PARAMETER", "UNUSED_VARIABLE")

package org.jonnyzzz.demo.proxy6

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


interface Service {
  fun doTheJob(): String
}

class ServiceException : Exception()

val classLoader get() = ServiceException::class.java.classLoader
fun main() {

  //the handler

  val handler = InvocationHandler { proxy, method, args ->
    //let's make Service throw exceptions!
    throw ServiceException()
  }

  // implement Service implicitly
  val service = Proxy.newProxyInstance(
          classLoader,
          arrayOf(Service::class.java),
          handler
  ) as Service

  //let's call the Service#doTheJob
  service.doTheJob()

}


fun pre() {


  val handler = object: InvocationHandler {

    override fun invoke(proxy: Any?,
                        method: Method?,
                        args: Array<out Any>?): Any {
      //let's make Service throw exceptions!
      throw ServiceException()
    }
  }



}


fun main2() {

  //the handler
  val handler = InvocationHandler {                 _, _, _ ->
    //let's make Service throw exceptions!
    throw ServiceException()
  }

  // implement Service implicitly
  val service = Proxy.newProxyInstance(
          classLoader,
          arrayOf(Service::class.java),
          handler
  ) as Service


  //let's call the Service#doTheJob
  service.doTheJob()

}
