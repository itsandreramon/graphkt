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
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(project(":io"))
    implementation(kotlin("stdlib"))

    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}