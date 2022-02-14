import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.diffplug.spotless") version "6.2.1"
}

group = "app"

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_11

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

tasks.test {
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

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
}