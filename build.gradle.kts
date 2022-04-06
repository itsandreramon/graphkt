/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    id("com.diffplug.spotless") version "6.2.1"
}

group = "app"

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17

subprojects {
    apply(plugin = "com.diffplug.spotless")

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            targetExclude("spotless.license.kt")
            licenseHeaderFile(rootProject.file("spotless.license.kt"))
        }
    }
}