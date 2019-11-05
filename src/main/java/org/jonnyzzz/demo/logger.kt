@file:Suppress("unused", "UNUSED_VARIABLE", "PackageDirectoryMismatch", "PropertyName")
package org.jonnyzzz.demo.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory


  inline fun <reified T: Any> T.logger(): Logger
          = LoggerFactory.getLogger(T::class.java)

  object Object {
    val LOG = logger()
  }
  class Class {
    val LOG = logger()
  }

