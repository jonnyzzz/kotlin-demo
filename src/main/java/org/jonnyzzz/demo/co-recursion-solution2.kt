@file:Suppress("PackageDirectoryMismatch")
@file:OptIn(ExperimentalStdlibApi::class)

package org.jonnyzzz.demo.corecursionSolution

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


  val deepSum = DeepRecursiveFunction<Long, Long> { x ->
    if (x <= 1) return@DeepRecursiveFunction 1
    1L + callRecursive(x - 1)
  }

  fun main()  {
    println(deepSum(100_000))
  }


