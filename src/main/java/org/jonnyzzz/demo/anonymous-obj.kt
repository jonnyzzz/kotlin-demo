@file:Suppress("PackageDirectoryMismatch", "UNUSED_VARIABLE", "unused")

package org.jonnyzzz.demo.anonymousObj


interface A
interface Multiple
interface Intfs
interface Ok

fun mocj() {


  val anonymousObject =
          object : Multiple, Intfs, Ok {
            fun questionOfUniverse() = 42
          }

  //access anonymous declarations
  anonymousObject.questionOfUniverse()


}
