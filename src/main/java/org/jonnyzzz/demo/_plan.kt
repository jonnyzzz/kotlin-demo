@file:Suppress("PackageDirectoryMismatch")
@file:JvmName("KotlinPresentationPlan")
package org.jonnyzzz.demo.plan


val plan = buildPlan {

  + "show new project wizard (again) => we create JAR"

  + "main function"

  + "iterate args"
  + "string template"
  + "extract function"
  + "extension function and properties"
  + "default parameters"

  + "function to pick the best hello message"
  + "UserInfo class with nullable hello message"
  + "function to load all UserInfos from the database"
  + "TODO function for that one"

  + "code findHelloMessage function with" {
    + "search for var bestUser : UserInfo? (like it was in Java)"
    + "fail to read hello from a nullable bestUser"
    + "return the checked non-null hello"
    + ".find() function on collections"
    + "turn it to the single-line: col?.find{}?.xxx ?: foo"
    + "hard-coded cases - if/when"
  }

  + "convert to if/else with == on strings"
  + "ask if == is safe on Java"
  + "overloaded == operator"

  + "add bag weight to the user"
  + "compute total weight as expression"
  + "TASK: compute bags count"


  + "data classes and copy"
  + "nullability"


}



fun main() {
  println("That is the plan:")
  println(plan)
}

private interface PlanBuilder {
  operator fun Unit.unaryPlus() {}

  operator fun String.unaryPlus()
  operator fun String.invoke(action : PlanBuilder.() -> Unit)
}

private fun buildPlan(action: PlanBuilder.() -> Unit) : String = buildString {
  object : PlanBuilder {
    override fun String.unaryPlus() {
      append(" + ").appendln(this).appendln()
    }

    override fun String.invoke(action: PlanBuilder.() -> Unit) {
      append(buildPlan(action))
    }
  }.action()

}