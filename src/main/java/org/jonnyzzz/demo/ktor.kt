@file:Suppress("PackageDirectoryMismatch")

package org.jonnyzzz.demo.ktor

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.PartialContent
import io.ktor.features.XForwardedHeaderSupport
import io.ktor.html.respondHtml
import io.ktor.jackson.jackson
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.strong
import kotlinx.html.title
import java.text.DateFormat
import java.util.*


fun main() {
  embeddedServer(Netty, 9232) {
    install(DefaultHeaders)
    install(PartialContent)
    install(XForwardedHeaderSupport)
    install(ContentNegotiation) {
      jackson {
        dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.US)
        registerKotlinModule()
        enable(SerializationFeature.INDENT_OUTPUT)
      }
    }

    install(Routing)
    routing {
      get {
        call.respondHtml {
          head {
            title = "ktor!"
          }
          body {
            h1 { +"Hello!" }
            +" This is "
            +" HTML "
            strong { +" in Kotlin DSL " }
          }
        }
      }

      get("/health") {
        call.respondText { "OK" }
      }

    }
  }.start(wait = true)
}

