@file:Suppress("PackageDirectoryMismatch")
@file:JvmName("KotlinPresentationPlan")
package org.jonnyzzz.demo.plan.tum

import java.lang.StringBuilder

val plan = buildPlan {

  "anonymous object" {
    + "implement multiple interfaces"
    + "access members directly"
  }

  "extensions" {
    + "extension function"
    + "extension property"
    + "ordinary live coding with DSLs"
    + "extension with a receivers"
    + "quick DSLs"
  }

  "also, apply, let, run" {
    + "code if and initialization"
    + "with {}"
    + "use {}"
    + "Local and non-local returns"
  }

  + "collections (compare with Java)"

  + "lazy"

  "Nothing" in slides {
    + "elvis"
    + "several types"
    + "how to simplify code"
    + ("use Kotliners slides")
  }

  "reified generics and type erasure" in slides {
    + "show bytecode"
    + "declarations clash"
    + "cast example"
    + "json example with reflection"
  }

  "sequences with coroutines" in slides {
    + ("use Coroutines presentation")
  }

  "injected languages" {
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

  fun slides(function: PlanBuilder.() -> Unit) = function

  operator fun (PlanBuilder.() -> Unit).contains(block: String): Boolean {
    "[SLIDES] $block" {
      this@contains()
    }
    return true
  }
}


private fun buildPlan(action: PlanBuilder.() -> Unit) : String = buildString {
  buildPlan(this, "", action)
}

private fun buildPlan(sb: StringBuilder,
                      offset: String,
                      action: PlanBuilder.() -> Unit) {
  object : PlanBuilder {
    override fun String.unaryMinus() {
      sb.append(offset).append(" - ").appendln(this)
    }

    override fun String.unaryPlus() {
      sb.append(offset).append(" + ").appendln(this)
    }

    override fun String.invoke(action: PlanBuilder.() -> Unit) {
      +this
      buildPlan(sb, "$offset  ", action)
      sb.appendln()
    }
  }.action()
}
