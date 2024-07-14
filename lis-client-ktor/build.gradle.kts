plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.0.0"
}

val ktorVersion: String by project
val logbackVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}