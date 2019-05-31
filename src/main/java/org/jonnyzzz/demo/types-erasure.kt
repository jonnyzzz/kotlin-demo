@file:Suppress("PackageDirectoryMismatch", "unused")

package org.jonnyzzz.demo.erasure

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper


inline fun <reified T> loadJsonObject(text: String): T {
  return ObjectMapper().readValue(text, object : TypeReference<T>() {})
}

val loadGeneric: List<Map<String, List<Int>>> = loadJsonObject("[{\"a\":[42]}]")


fun main() {
  println(loadGeneric)
}


  fun <T> genericFunction(t: T): List<String>
          where T : Number,
                T : Runnable {
    t.run()
    return listOf("123" + t.toDouble())
  }




