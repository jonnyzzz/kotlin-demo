@file:Suppress("PackageDirectoryMismatch", "unused")
package org.jonnyzzz.demo.cocallbacks3

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine




  fun funWithCallback(onDone: (String) -> Unit) {
    onDone("@jonnyzzz")
  }
  suspend fun coFun() = suspendCoroutine<String> { cont ->
      funWithCallback {
        //resume coroutine & return the value
        cont.resume(it)
      }
    }

  fun justFun() = runBlocking {
    coFun()
  }




val q = ::funWithCallback
val p = ::coFun