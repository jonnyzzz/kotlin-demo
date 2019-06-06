@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.corecursionSolution

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


fun sum(x: Long): Long {
  if (x <= 1) return 1
  return 1 + sum(x - 1)
}

  suspend fun sum(x: Int): Int {
    //free call stack - suspend it
    yield()

    if (x <= 1) return 1
    return 1 + sum(x - 1)
  }

  fun main() = runBlocking {
    println(sum(100_000))
  }

