@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.erasure4

import java.lang.reflect.ParameterizedType


  abstract class TypeReference<T>

  inline fun <reified Y> captureType(): TypeReference<Y> {
    return object : TypeReference<Y>() {}
  }

  //new anonymous class is declared and created here
  val capturedType = captureType<Map<String, Int>>()

  //use Java reflections to get the type
  val theType = capturedType.javaClass
          .let { it.genericSuperclass as ParameterizedType }
          .actualTypeArguments.joinToString()
  //prints
  //java.util.Map<java.lang.String, ? extends java.lang.Integer>


fun main() {
  println(theType)
}
