plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":excel-wrapper"))
    implementation(project(":common"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}