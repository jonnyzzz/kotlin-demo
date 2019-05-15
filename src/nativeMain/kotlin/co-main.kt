package org.jonnyzzz.mpp

import kotlinx.cinterop.StableRef
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKStringFromUtf8
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


fun example() {
  val text = runBlocking { asCallback() }
  println("text is '$text'")
}

suspend fun asCallback() = memScoped {
  suspendCoroutine<String> { cont ->
    val stableRef = StableRef.create(cont)
    defer { stableRef.dispose() }

    returnStringFromCallback(stableRef.asCPointer(), staticCFunction { context, str ->
      context!!.asStableRef<Continuation<String>>().get().resume(
              str?.toKStringFromUtf8() ?: "NULL"
      )
    })
  }
}
