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
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.mindrot:jbcrypt:0.4")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}