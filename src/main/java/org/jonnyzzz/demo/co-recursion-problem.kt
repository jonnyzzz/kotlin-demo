@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.corecursionProblem


fun sum(x: Long): Long {
  if (x <= 1) return 1
  return 1 + sum(x - 1)
}

fun main() {
  sum(1000)
}

