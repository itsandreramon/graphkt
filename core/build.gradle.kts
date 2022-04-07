/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

plugins {
    kotlin("jvm")
}

group = "app"

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":io"))
    implementation(kotlin("stdlib"))
}