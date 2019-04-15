plugins {
  kotlin("jvm") version "1.3.30"
  `application`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.0")
}

application {
  mainClassName = "org.jonnyzzz.template.jvm.MainKt"
}

