@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.bubble

// https://en.wikipedia.org/wiki/Bubble_sort


fun bubbleSort(a: IntArray) {
  for (i in a.size downTo 0) {
    for (j in 1 until i) {
      if (a[j - 1] > a[j]) {
        val t = a[j - 1]
        a[j - 1] = a[j]
        a[j] = t
      }
    }
  }

}


fun main() {

  fun testMe(vararg a: Int) {
    bubbleSort(a)
    println(a.toList())
    if (a.toList() != a.toList().sorted()) {
      println(" Invalid Sort!")
    }
  }

  testMe()
  testMe(1)
  testMe(1, 2)
  testMe(3, 1)
  testMe(3, 1, 2)
  testMe(3, 2, 1)
  testMe(3, 3, 3)
  testMe(1, 3, 2, 5)
  testMe(1, 2, 3, 4, 5)
  testMe(5, 2, 3, 1, 4)
}
