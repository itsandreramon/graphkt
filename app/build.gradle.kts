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
    implementation(project(":core"))
    implementation(project(":gen-api-kotlin"))

    implementation(kotlin("stdlib"))

    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}