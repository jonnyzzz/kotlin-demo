@file:Suppress("PackageDirectoryMismatch")
@file:JvmName("KotlinPresentationPlan")
package org.jonnyzzz.demo.plan.tum

val plan = buildPlan {
  + "extension properties" {
    + "extension function"
    + "extension property"
    + "may do via ordinary live coding with DSLs"
  }

  + "lazy"

  + "sequences with coroutines" {
    + ("use Coroutines presentation")
  }

  + "also, apply, let, run" {
    + "code if and initialization"
    + "with {}"
    + "use {}"
  }

  + "Nothing" {
    + "elvis"
    + "several types"
    + "how to simplify code"
    + ("use Kotliners slides")
  }

  + "Local and non-local returns"
  + "collections (compare with Java)"

  + "reified generics and type erasure" {
    + "show bytecode"
    + "declarations clash"
    + "cast example"
    + "json example with reflection"
  }

  + "injected languages" {
    + "@Language annotation"
    + "js() function"
    + "quick fix action"
  }
}


fun main() {
  println("That is the plan:")
  println(plan)
}

private interface PlanBuilder {
  operator fun Unit.unaryPlus() {}

  operator fun String.unaryPlus()
  operator fun String.unaryMinus()
  operator fun String.invoke(action : PlanBuilder.() -> Unit)
}

private fun buildPlan(action: PlanBuilder.() -> Unit) : String = buildString {
  object : PlanBuilder {
    override fun String.unaryMinus() {
      append(" - ").appendln(this).appendln()
    }

    override fun String.unaryPlus() {
      append(" + ").appendln(this).appendln()
    }

    override fun String.invoke(action: PlanBuilder.() -> Unit) {
      append(buildPlan(action))
    }
  }.action()

}