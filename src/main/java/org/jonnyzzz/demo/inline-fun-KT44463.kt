package org.jonnyzzz.demo

/// https://youtrack.jetbrains.com/issue/KT-44463
inline fun x(vararg y: Int, f: (Int) -> Int) {
    var z = 0
    for (i in y) {
        z += f(i)
    }
}
