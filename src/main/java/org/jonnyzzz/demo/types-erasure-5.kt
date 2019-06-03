@file:Suppress("PackageDirectoryMismatch", "unused", "UNUSED_PARAMETER")

package org.jonnyzzz.demo.erasure5


object X {


  @JvmName("!items")
  fun processItems(items: List<String>) {
  }

  @JvmName("!meta")
  fun processItems(items: List<MetaItem>) {
  }


  class MetaItem


}