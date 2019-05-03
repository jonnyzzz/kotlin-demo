@file:Suppress("PackageDirectoryMismatch", "unused", "UNUSED_PARAMETER")
package org.jonnyzzz.demo.json


interface JSONObjectBuilder {
  infix fun String.to(value: Int)
  infix fun String.to(value: String)


  infix fun String.array(builder: JSONArrayBuilder.() -> Unit)
  infix fun String.obj(builder: JSONObjectBuilder.() -> Unit)
}

interface JSONArrayBuilder {
  fun add(x: Int)
  fun add(x: String)
}


fun buildJSONObject(builder: JSONObjectBuilder.() -> Unit) {

}

fun buildJOSNArray(builder: JSONArrayBuilder.() -> Unit) {

}


fun main() {

  buildJSONObject {

    "aaa" to 4

    "bbb" array {
      add("value")
    }

    "ccc" obj {
      "ddd" to 42
    }

  }


}