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
    val main by compilations
    main.cinterops.create("libpng") {
      defFile = file("src/nativeMain/kotlin/lib.def")
      packageName = "org.jonnyzzz.mpp"
    }

    binaries {
      executable {
        entryPoint = "org.jonnyzzz.mpp.main"
      }
    }
  }

  

  val commonMain by sourceSets.getting
  val nativeMain by sourceSets.getting
  val javaMain by sourceSets.getting
  val jsMain by sourceSets.getting

  val coroutinesVersion = "1.2.1"
  commonMain.dependencies {
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion")
  }
  
  nativeMain.dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutinesVersion")
  }

  javaMain.dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
  }

  jsMain.dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
  }
}



tasks.getByName<Exec>("runDebugExecutableNative") {
  doFirst {
    println()
    println("Running $commandLine")
    println()
  }
}

