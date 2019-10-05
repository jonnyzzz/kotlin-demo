@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.testPrimes

import java.util.*
import kotlin.collections.HashSet

fun main() {
  //yet another experiment https://twitter.com/fermatslibrary/status/1180470450083770369
  /****** - 19993319
   ***** - 23399339
   ***** - 29399999
   ***** - 37337999
   ***** - 59393339
   ***** - 73939133
   ***** - 197933933*/
  testSimple(73939133)

  listPrimes()
}

private fun listPrimes() {
  val numbers = TreeSet<Entry>()
  numbers += Entry(2)

  val nextNonPrime = sequence {
    while(true) {
      val entry = numbers.pollFirst() ?: return@sequence
      numbers.add(entry.next())
      yield(entry.value)
    }
  }.iterator().peek()

  val primesSequence = sequence<Long> {
    yield(2)

    var c = 1L
    while (true) {
      c += 2
      while (nextNonPrime.peek < c) nextNonPrime.next()
      if (nextNonPrime.peek == c) continue

      numbers.add(Entry(c))
      yield(c)
    }
  }

  val bs = HashSet<Long>()
  tailrec fun simm(it: Long) : Boolean {
    if (it <= 1) return true
    if (!bs.contains(it)) return false
    return simm(it / 10)
  }

  primesSequence.forEach {
    //    testPrime(it)
    bs.add(it)

    if (simm(it)) {
      println("***** - $it")
    }
  }
}


fun testPrime(p: Long) {
  val div = (2L until p).firstOrNull { p % it == 0L }
  require(div == null) { "$p is divisible by $div"}
}

fun testSimple(p: Long) {
  if (p <= 1L) return
  testPrime(p)
  println(p)
  testSimple(p / 10)
}


data class Entry(val base: Long, val mul: Long = 1) : Comparable<Entry> {
  val value = base * mul

  fun next() = copy(mul = mul + 1)

  override fun compareTo(other: Entry): Int {
    val x = value.compareTo(other.value)
    return if (x != 0) x else base.compareTo(other.base)
  }

  override fun equals(other: Any?): Boolean {
    val entry = other as? Entry ?: return false
    return entry.value == value && entry.base == this.base
  }

  override fun hashCode() = value.hashCode()

  override fun toString() = "Entry{ value = $value, base=$base }"
}

class PeekingIterator<T : Any>(private val base: Iterator<T>) : Iterator<T> {
  private var peekValue : T? = null

  val peek get() = peekValue ?: run {
    val next = base.next()
    peekValue = next
    next
  }

  override fun hasNext() = peekValue != null || base.hasNext()

  override fun next(): T {
    val value = peek
    peekValue = null
    return value
  }
}

fun <T : Any> Iterator<T>.peek() = PeekingIterator(this)

