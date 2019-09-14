@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.lists

fun main() {

  listOf("A")
          .asSequence()
          .map { it.length }
          .filter { it > 0 }
          .toSet()
          .joinToString()


}


