plugins {
  kotlin("multiplatform") version "1.3.30"
}

repositories {
  mavenCentral()
}


kotlin {

  //TODO: use linuxX64 (on Linux) or mingwX64 (on Windows)
  macosX64("native") {
    binaries {
      executable {
        entryPoint = "org.jonnyzzz.mpp.main"
      }
    }
  }

}

tasks.getByName<Exec>("runDebugExecutableNative") {
  doFirst {
    println()
    println("Running $commandLine")
    println()
  }
}
