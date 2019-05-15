@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.guarded2

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

fun main() {
  val room = MeetupRoom()

  val b = CyclicBarrier(101)
  List(b.parties - 1) {
    thread {
      b.await()

      if ((0..5).random() <= 2) {
        room.onSomeoneCame()
      }
    }
  }.also { b.await() }.map { it.join() }

  if ((0..8).random() <= 4) {
    room.onSpeakerAppeared()
  }

  println(room.runTheTalk())
}

class MeetupState {
  var speakerAppeared = false
  var peopleInTheRoom = 0
}

class Guard {
  private val lock = ReentrantLock()
  private val state = MeetupState()

  inline operator fun <T> invoke(action: MeetupState.() -> T) : T = state.action()
}

class MeetupRoom {

  private val state = Guard()

  fun onSpeakerAppe2ared() = state { peopleInTheRoom++ }

  fun onSpeakerAppeared() = state {
      speakerAppeared = true
    }

  fun onSomeoneCame() {
    state {
      peopleInTheRoom++
    }
  }

  fun runTheTalk() : String{

    state F@{
      if (!speakerAppeared) return@F "Let's see the same speaker from YouTube, we had $peopleInTheRoom attendees"
      return "Thank you, we had $peopleInTheRoom attendees in this room"
    }

    println("wesdf")
  }
}
