package org.jonnyzzz.mpp


fun fibonacci() = sequence<Int> {
  TODO("not implemented")
}


expect fun implementationName() : String


fun printFibonacci() {
  println("Computing Fibonacci - ${implementationName()}")
  println()
  fibonacci().take(10).forEach(::println)
}


