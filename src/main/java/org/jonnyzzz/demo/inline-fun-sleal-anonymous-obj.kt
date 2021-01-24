@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "DuplicatedCode", "ObjectLiteralToLambda"
)
package org.jonnyzzz.demo.stealObj

import java.util.concurrent.Callable


    /** Every call-side will have it's unique class created **/
    inline fun <reified T> newLambdaClass(crossinline x: () -> T): Class<*> {
        val mockClass = object : Callable<T> {
            override fun call(): T = x()
        }
        return mockClass.javaClass
    }



fun main() {

    val example1 = newLambdaClass { println("test") }
    val example2 = newLambdaClass { println("test") }
    require(example1 !== example2)

    //this is how we can deal with implicit the class, it depends on the capture params!
    val copy = example1.getConstructor().newInstance() as Callable<*>
    copy.call()


    val example3 = newLambdaClass { example1.toString() }
    println("Example 3:")
    example3.constructors.forEach { println("  params: ${it.parameterTypes.joinToString(", ")}") }

}




