@file:Suppress("PackageDirectoryMismatch", "UnusedMainParameter")
package org.jonnyzzz.demo.main

fun loadAllUsersForTheDemoNotForProduction(): List<UserInfo> = TODO()


data class UserInfo(val name: String, val hi: String?)

fun findHello(name: String) : String {
  return loadAllUsersForTheDemoNotForProduction()
          .filter { user -> user.name == name }
          .mapNotNull { it.hi }
          .firstOrNull() ?: when {
    name.equals("Eugene", ignoreCase = true) -> "Privet"
    name == "Oracle" -> "Good Evening!"
    else -> "Hello"
  }
}


fun hello(name: String, hi: String = findHello(name)) = """
  $hi, 
  ${name.capitalize()}!
  """.trimIndent()

val String.helloMessage
  get() = helloMessage()

fun String.helloMessage() = hello(this)

fun main(args: Array<String>) {
  println(
          hello(hi = "Hi", name = "San Francisco")
  )

  for (name in args) {
    println( name.helloMessage )
  }
}

