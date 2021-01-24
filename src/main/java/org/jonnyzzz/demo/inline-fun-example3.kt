@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName")
package org.jonnyzzz.demo.inlinefun3


class Lock {
    fun lock() = Unit
    fun unlock() = Unit
}

fun doSomeWork(i: Int) {

}

fun we_need(l: Lock, i: Int) {
    l.lock()
    try {
        doSomeWork(i)
    } finally {
        l.unlock()
    }
}

fun we_want(l: Lock, i: Int) {

    /// now we can write with inline
    lock(l) {
        doSomeWork(i)
    }

}


    inline fun <Y> lock(l: Lock, action: () -> Y) : Y {
        l.lock()
        try {
            return action()
        } finally {
            l.unlock()
        }
    }


