plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.0.10"
}

group = "cz.lukynka.cloudsettingssync"
version = parent!!.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.kotlinx)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}