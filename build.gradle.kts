plugins {
    kotlin("jvm") version "2.1.10"
}

group = "cz.lukynka.cloudsettingssync"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://mvn.devos.one/releases")
        maven("https://mvn.devos.one/snapshots")
        maven("https://jitpack.io")
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}