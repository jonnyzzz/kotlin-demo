package org.jonnyzzz.template.jvm

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger


inline val dispatcherName get() = Thread.currentThread().name.padStart(20)


fun named(name: String) = object : ThreadFactory {
  private val counter = AtomicInteger(0)
  override fun newThread(r: Runnable) = Thread(r, "$name-${counter.incrementAndGet()}").also { it.isDaemon = true }
}

