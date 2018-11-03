package org.jonnyzzz.demo

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


fun main2(args: Array<String>) {
  runBlocking {
    `suspend fun`()
  }
}

suspend fun `suspend fun`() {

  println("on start: " + Thread.currentThread())
  callWithResult()
  println("on end: " + Thread.currentThread())
}

suspend fun callWithResult() = suspendCancellableCoroutine<String> { cont ->
  someLongRunningAsyncAction(object : ActionListener<String> {
    override fun onResponse(response: String) = cont.resume(response)
    override fun onFailure(e: Exception) = cont.resumeWithException(e)
  })
}

interface ActionListener<Response> {
  fun onResponse(response: Response)
  fun onFailure(e: Exception)
}

fun someLongRunningAsyncAction(r: ActionListener<String>) {
  thread(name = "callback thread!") {
    println("on callback: " + Thread.currentThread())
    r.onResponse("hohoho!")
  }
}

fun powers(seed: Long) = sequence {
  var v = 1L
  while (true) {

    yield(v)

    v += seed
  }
}

fun main() = runBlocking {
  val jobs = List(100_000) {
    launch {
      delay(1000L)
      print(".")
    }
  }

  jobs.forEach { it.join() }
}

fun main(args: Array<String>) {
  main()
}

