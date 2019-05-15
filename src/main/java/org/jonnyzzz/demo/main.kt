@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.main

import corp.model.JavaClient
import corp.model.JavaClientBuilder
import corp.model.JavaCompanyBuilder
import corp.model.JavaTwitterBuilder
import java.util.function.Consumer

fun JavaClient.toDebugString() = "User(${twitter.handle})"

fun buildClient(action: JavaClientBuilder.() -> Unit): JavaClient {
  val builder = JavaClientBuilder()

  builder.action()

  return builder.build()
}

fun JavaClientBuilder.twitter(action : JavaTwitterBuilder.() -> Unit) {
  val builder = JavaTwitterBuilder()
  builder.action()
  twitter = builder.build()
}

fun JavaClientBuilder.company(action : JavaCompanyBuilder.() -> Unit) {
  JavaCompanyBuilder().apply(action).build()
}

fun main() {
  val client = buildClient {

    firstName = "Eugene"
    lastName = "Petrenko"

    twitter {
      handle = "@jonnyzzz"
    }

    company {
      name = "JetBrains"
      city = "Munich"
    }
  }

  println("Created client is: ${client.toDebugString()}")
}

