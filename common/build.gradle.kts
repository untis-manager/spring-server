plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.untis"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}