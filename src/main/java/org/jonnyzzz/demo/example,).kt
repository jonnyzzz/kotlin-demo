@file:Suppress("SpellCheckingInspection")

package org.jonnyzzz.demo


fun main1() {

  val names = listOf(
          "Eugene",
          "@jonnyzzz", // <-- ,)
  )

  println( names)

}

fun main2() {

  val names = listOf(
          "Eugene",
          "@jonnyzzz", // <--- no line change to
          "Kotlin"
  )


  println( names)

}