@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.c2

import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


fun main() {

  runBlocking/*(Dispatchers.Unconfined)*/ {
    repeat(5) {
      println("${Thread.currentThread().id} - $it")
      val x = suspendCoroutine<String> {
        thread {
          it.resume("123-${Thread.currentThread().id}")
        }
      }
      println("${Thread.currentThread().id} - $x $it")
    }
  }
}