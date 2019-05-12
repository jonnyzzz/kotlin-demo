@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
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
  MyScope().apply {
    try {
      playWithCoroutine()
    } catch (t: Throwable) {
      println("ERRROR")
    }
  }

  val jobs = List(100_000) {
    launch {
      delay(1000L)
//      print(".")
    }
  }

  jobs.forEach { it.join() }
}




class MyScope : CoroutineScope {
  private val job = Job()

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Default + job


  fun playWithCoroutine() {
    launch {
      println("Hello from Coroutine")

      val result = async { runHeavyTask() }

      withContext(Dispatchers.IO) {
        showResult(result.await())
      }
    }
  }


  private fun showResult(text: String) {
    println("Show Result: $text")
  }

  private fun runHeavyTask() : String = throw Error("asdasd")

  fun dispose() {
    job.cancel()
  }
}
