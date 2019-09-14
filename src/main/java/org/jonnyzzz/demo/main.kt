@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.main

data class User(
        val name: String,
        val hello: String? = null
)

fun readAllUsers() : List<User> = TODO()

fun guessHello(name: String) : String {

  return readAllUsers()
          .filter {
            it.name == name
            return "sdfs"
          }
          .map { it.hello }
          .firstOrNull() ?: when(name) {
    "Space" -> "Привет"
    "Business" -> ":("
    else -> "Hello"
  }
}

fun helloMessage(name: String,
                 hello: String = guessHello(name))
        = "$hello, ${name.capitalize()}!"

fun String.hello() = helloMessage(this)
val String.hello
  get() = helloMessage(this)


fun main(args: Array<String>) {
  println(helloMessage(
          hello = "Hello",
          name = "Space")
  )

  for (name in args) {
    println(
            helloMessage(name)
    )
  }
}

