@file:Suppress("PackageDirectoryMismatch", "unused")
package org.jonnyzzz.demo.cocallbacks2

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine




  fun funWithCallback(onDone: (String) -> Unit) {
    onDone("@jonnyzzz")
  }

  fun justFun() = runBlocking {
    suspendCoroutine<String> { cont ->
        funWithCallback {
          //resume coroutine & return the value
          cont.resume(it)
        }
      }
    }




val q = ::funWithCallback