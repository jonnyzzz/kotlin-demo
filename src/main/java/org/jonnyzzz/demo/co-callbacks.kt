@file:Suppress("PackageDirectoryMismatch", "unused")
package org.jonnyzzz.demo.cocallbacks

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

  fun funWithCallback(onDone: (String) -> Unit) {
    onDone("@jonnyzzz")
  }

  fun justFun(): String = runBlocking { coFun() }

  suspend fun coFun(): String {
    return suspendCoroutine { cont ->
      funWithCallback {
        //resume coroutine & return the value
        cont.resume(it)
      }
    }
  }





val q = ::funWithCallback