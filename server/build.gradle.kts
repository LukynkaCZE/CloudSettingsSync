plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.0.10"
}

group = "cz.lukynka.cloudsettingssync.server"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":common"))
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.lkws)
    implementation(libs.hollow)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}