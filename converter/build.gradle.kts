plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.0.0"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    implementation(project(":excel-dsl"))
    implementation(project(":common"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}