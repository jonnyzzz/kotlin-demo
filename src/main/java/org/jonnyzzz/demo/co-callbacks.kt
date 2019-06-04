@file:Suppress("PackageDirectoryMismatch", "unused")
package org.jonnyzzz.demo.cocallbacks

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


  fun function(): String = runBlocking {
    suspendCoroutine<String> { cont ->
      functionWithCallback {
        //resume coroutine & return the value
        cont.resume(it)
      }
    }
  }

  fun functionWithCallback(onDone: (String) -> Unit) {
    onDone("@jonnyzzz")
  }



val q = ::functionWithCallback