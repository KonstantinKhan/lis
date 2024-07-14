plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test"))

    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.5.2")
}

tasks.test {
    useJUnitPlatform()
}