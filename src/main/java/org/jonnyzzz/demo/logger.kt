@file:Suppress("unused", "UNUSED_VARIABLE", "PackageDirectoryMismatch", "PropertyName", "HasPlatformType", "PrivatePropertyName")
package org.jonnyzzz.demo.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory




  inline fun <reified T: Any> T.logger(): Logger
          = LoggerFactory.getLogger(T::class.java)

  class MyService2 {
    private val LOG = logger()
  }

  class MyService {
    companion object {
      private val LOG = logger()
    }
  }





class Scope2 {
  inline fun <reified T: Any> T.logger(): Logger
          = LoggerFactory.getLogger(T::class.java)


  class MyService {
    companion object {
      private val LOG = LoggerFactory
              .getLogger(CopyPasteUnsafe::class.java)
    }
  }



  class CopyPasteUnsafe

}