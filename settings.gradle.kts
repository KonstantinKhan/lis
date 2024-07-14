rootProject.name = "lis"

pluginManagement {
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include("lis-app-ktor")
include("lis-client-ktor")
include("file-structure-service")
include("excel-wrapper")
include("excel-dsl")
include("converter")
include("common")
