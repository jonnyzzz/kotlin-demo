plugins {
  kotlin("multiplatform") version "1.3.30"
}

repositories {
  mavenCentral()
}


kotlin {
  jvm("java")
  js("js")

  //TODO: use linuxX64 (on Linux) or mingwX64 (on Windows)
  macosX64("native") {
    binaries {
      executable {
        entryPoint = "org.jonnyzzz.mpp.main"
      }
    }
  }

  

  val commonMain by sourceSets.getting
  val javaMain by sourceSets.getting
  val jsMain by sourceSets.getting
  
  commonMain.dependencies {
    implementation(kotlin("stdlib-common"))
  }
  
  javaMain.dependencies {
    implementation(kotlin("stdlib"))
  }
  
  jsMain.dependencies {
    implementation(kotlin("stdlib-js"))
  }
}

