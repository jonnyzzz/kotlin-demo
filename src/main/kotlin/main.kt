package org.jonnyzzz.template.jvm

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
  val room = MeetupRoom()

  val b = CyclicBarrier(101)
  List(b.parties - 1) {
    thread {
      b.await()

      repeat(10) {
        Thread.yield()
        if ((0..5).random() <= 2) {
          room.onSomeoneCame()
        }
      }
    }
  }.also { b.await() }.map { it.join() }

  if ((0..8).random() <= 4) {
    room.onSpeakerAppeared()
  }

  room.`method with bug`()

  println(room.runTheTalk())
}


//TODO: extract state to a dedicated class named State
//TODO: create class Guard that holds the state as [private val]
//TODO: fix the MeetupRoom to ensure locks are used
//TODO: (use ReentrantLock.withLock{..} for shorted version)

class MeetupRoom {
  private val lock = ReentrantLock()

  private var speakerAppeared = false
  private var peopleInTheRoom = 0

  fun `method with bug`() {
    peopleInTheRoom *= if (speakerAppeared) 3 else 7
  }

  fun onSpeakerAppeared() {
    lock.lock()
    try {
      speakerAppeared = true
      peopleInTheRoom++
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
