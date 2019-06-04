@file:Suppress("PackageDirectoryMismatch", "unused")

package org.jonnyzzz.demo.erasure

import kotlin.reflect.KClass

fun main() {
  println(inlineCast<List<String>>())
}

  fun <T> genericFunction(t: T): List<String>
          where T : Number,
                T : Runnable {
    t.run()
    return listOf("123" + t.toDouble())
  }


  fun <T> genericCast(t: Any): T {
    return t as T
  }

  inline fun <reified Y : Any> inlineCast(): KClass<Y> = Y::class


class KotlinService
inline fun <reified T> regObject() {}
inline fun <reified T> parseObject() : T = TODO()





fun theLastExample() {

  // register new object
  regObject<KotlinService>()

  // parse something
  val list = parseObject<List<String>>()



}





