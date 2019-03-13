package org.jonnyzzz.demo

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper


inline fun <reified T> loadJsonObject(text: String): T {
  return ObjectMapper().readValue(text, object : TypeReference<T>() {})
}

val loadGeneric : List<Map<String, List<Int>>> = loadJsonObject("[{\"a\":[42]}]")


fun main() {
  println(loadGeneric)
}
