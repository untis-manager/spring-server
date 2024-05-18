import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

group = "com.untis"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(project(":service"))
    implementation(project(":model"))

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.jsonwebtoken:jjwt-api:0.11.4")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.4")
    implementation("io.jsonwebtoken:jjwt-gson:0.11.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "19"
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(19)
}