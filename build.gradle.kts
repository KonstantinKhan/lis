plugins {
    kotlin("jvm") apply false
}

group = "com.khan366kos"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}