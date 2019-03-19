@file:Suppress("PackageDirectoryMismatch")
package org.jonnyzzz.demo.gamussa

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** API **/
data class ProcessorId(val name: String)
data class SourceId(val name: String)

interface ProcessorDelegate {
  operator fun provideDelegate(thisRef: Any?,
                               prop: KProperty<*>) : ReadOnlyProperty<Any?, ProcessorId>
}

interface ProcessorBuilder {
  fun readFrom(vararg sourceId: SourceId)

  fun <K,V> keyValueStore(keyType: Class<K>,
                          valueType: Class<V>,
                          name: String)

}


fun <T> processor(processorClass: Class<T>, name: String, action: ProcessorBuilder.() -> Unit) : ProcessorDelegate {
  object: ProcessorBuilder {
    override fun readFrom(vararg sourceId: SourceId) = TODO("not implemented")
    override fun <K, V> keyValueStore(keyType: Class<K>, valueType: Class<V>, name: String) = TODO("not implemented")
  }.action()

  return object:ProcessorDelegate {
    override fun provideDelegate(thisRef: Any?, prop: KProperty<*>) = object:ReadOnlyProperty<Any?, ProcessorId> {
      override fun getValue(thisRef: Any?, property: KProperty<*>) = ProcessorId(name)
    }
  }
}

/** inline functions to simplify generics**/

inline fun <reified K, reified V> ProcessorBuilder.keyValueStore(name: String)
        = keyValueStore(K::class.java, V::class.java, name)

inline fun <reified T> processor(name: String, noinline action: ProcessorBuilder.() -> Unit)
        = processor(T::class.java, name, action)


/** example **/

val source = SourceId("23")

class MyProcessor

val processor by processor<MyProcessor>("name") {
  readFrom(source)
  keyValueStore<String, Int>(name = "counts")  ///Serdes from type parameters, processor implicit
}


/** documentation references **/
//
// https://kotlinlang.org/docs/reference/delegated-properties.html#providing-a-delegate-since-1
//
// https://kotlinlang.org/docs/reference/object-declarations.html#object-expressions
//
