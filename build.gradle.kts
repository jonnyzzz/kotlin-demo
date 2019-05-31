import org.jetbrains.kotlin.gradle.tasks.*

plugins {
  `java-library`
  kotlin("jvm") version "1.3.31"
}

allprojects {
  repositories {
    jcenter()
    mavenCentral()
  }
}

group = "org.jonnyzzz"

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += listOf("-XXLanguage:+InlineClasses", "-Xuse-experimental=kotlin.contracts.ExperimentalContracts")
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  implementation("com.fasterxml.jackson.core:jackson-core:2.9.1")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.9.1")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.9.1")

  implementation("org.jdom:jdom2:2.0.6")
  implementation("org.jonnyzzz.kotlin.xml.bind:api:0.1.9")
  implementation("org.jonnyzzz.kotlin.xml.bind:jdom:0.1.9")
  implementation("org.jonnyzzz.kotlin.xml.dsl:api:0.1.6")
  implementation("org.jonnyzzz.kotlin.xml.dsl:jdom:0.1.6")
  
  implementation("io.reactivex.rxjava2:rxjava:2.2.2")
  implementation("com.squareup.okhttp3:okhttp:3.11.0")
  
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.2.1")

  val ktor_version = "1.2.1"
  implementation("io.ktor:ktor-server-netty:$ktor_version")
  implementation("io.ktor:ktor-html-builder:$ktor_version")
  implementation("io.ktor:ktor-locations:$ktor_version")
  implementation("io.ktor:ktor-jackson:$ktor_version")
  implementation("org.slf4j:slf4j-simple:1.7.26")
  implementation("com.google.guava:guava:23.3-jre")
  implementation("junit:junit:4.12")

  testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
}
