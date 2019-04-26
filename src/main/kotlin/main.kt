package org.jonnyzzz.template.jvm

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors


val downloadDispatcher = Executors.newFixedThreadPool(3, named("downloader")).asCoroutineDispatcher()
val mainDispatcher = Executors.newFixedThreadPool(1, named("UI")).asCoroutineDispatcher()


fun download(url: String): String {
  println("$dispatcherName - Download $url")
  Thread.sleep((100L..700L).random())
  return "downloaded-$url"
}

fun showUrl(url: String) : String {
  println("$dispatcherName - Show $url")
  Thread.sleep((100L..300L).random())
  return "downloaded - $url"
}

suspend fun main() = withContext(mainDispatcher) {
  println("$dispatcherName - Started")

  val urlsToDownload = listOf(
          "https://jonnyzzz.com",
          "https://jetbrains.com",
          "https://twitter.com/jonnyzzz"
  )

  urlsToDownload.forEach {
    val data = download(it)
    showUrl(data)
  }

  //TODO: make sure download runs in the Downloader thread pool
  //TODO: update UI from the UI thread only!
  //TODO: download asynchroniously?
}


