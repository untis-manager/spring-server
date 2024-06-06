plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.untis.common"
version = "unspecified"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}