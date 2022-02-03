plugins {
	kotlin("jvm") version "1.6.10"
}

group = "app"

repositories {
	mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17

tasks.compileKotlin {
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
	}
}

tasks.test {
	useJUnitPlatform()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation(kotlin("reflect"))

	testImplementation(platform("org.junit:junit-bom:5.8.2"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.junit.jupiter:junit-jupiter-params")
}