@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.corecursion

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


fun sum(x: Long): Long {
  if (x <= 1) return 1
  return 1 + sum(x - 1)
}

suspend fun coSum(x: Int): Int {
  yield()

  if (x <= 1) return 1
  return 1 + coSum(x - 1)
}

fun main() {
//  sum(1000)

  runBlocking {
    println(coSum(1000))
  }
}

