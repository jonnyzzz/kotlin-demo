@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.erasure.json

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

  inline fun <reified T> load(text: String): T {
    val type = object : TypeReference<T>() {}
    return ObjectMapper().readValue(text, type)
  }

  fun main() {
    data class MyData(val huge: List<Map<String, List<Int>>>)

    //type inference rocks!
    val clazz = MyData(load("[{\"a\":[42]}]"))
    println(clazz)

    //explicit generic arguments
    println(load<List<String>>("[\"s\"]"))
  }

