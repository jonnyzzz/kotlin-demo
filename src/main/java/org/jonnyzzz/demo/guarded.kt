@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.guarded2

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
  val room = MeetupRoom()

  if ((0..8).random() <= 4) {
    room.onSpeakerAppeared()
  }

  val b = CyclicBarrier(101)
  List(b.parties - 1) {
    thread {
      b.await()

      if ((0..2).random() <= 1) {
        room.onSomeoneCame()
      }
    }
  }.also { b.await() }.map { it.join() }

  println(room.runTheTalk())
}


class MeetupRoom {
  private val lock = ReentrantLock()

  private var speakerAppeared = false
  private var peopleInTheRoom = 0

  fun onSpeakerAppeared() {
    lock.lock()
    try {
      speakerAppeared = true
    } finally {
      lock.unlock()
    }
  }

  fun onSomeoneCame() {
    lock.lock()
    try {
      peopleInTheRoom++
    } finally {
      lock.unlock()
    }
  }

  fun runTheTalk(): String {
    lock.lock()
    try {
      if (!speakerAppeared) return "Let's see the same speaker from YouTube, we had $peopleInTheRoom attendees"
      return "Thank you, we had $peopleInTheRoom attendees in this room"
    } finally {
      lock.unlock()
    }
  }
}
