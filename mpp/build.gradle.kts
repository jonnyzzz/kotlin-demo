import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm()

  js {
    nodejs()
  }

  // UNCOMMENT TO ENABLE Kotlin/NAtive
  macosX64("native") {
    binaries {
      executable { }
    }
  }
}


val kotlinx_coroutines_version = "1.3.1"


kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$kotlinx_coroutines_version")
      }
    }

    val commonTest by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test-common")
        implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines_version")
      }
    }

    val jvmTest by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test")
        implementation("org.jetbrains.kotlin:kotlin-test-junit")
      }
    }

    val jsMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
        implementation("org.jetbrains.kotlin:kotlin-test-js")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$kotlinx_coroutines_version")
      }
    }
  }
}


tasks {
  val jsTestCompileClasspath by configurations.getting
  val compileTestKotlinJs by getting(KotlinJsCompile::class)
  val compileKotlinJs by getting(KotlinJsCompile::class)

  val populateNodeModules by creating(Sync::class) {
    afterEvaluate {
      into("$buildDir/node_modules")

      from(compileKotlinJs.kotlinOptions.outputFile)
      from(compileTestKotlinJs.kotlinOptions.outputFile)

      jsTestCompileClasspath.forEach {
        from(zipTree(it.absolutePath).matching {
          include("*.js")
          include("*.map")
        })
      }
    }

    compileKotlinJs.dependsOn(this)
    compileTestKotlinJs.dependsOn(this)
  }
}

