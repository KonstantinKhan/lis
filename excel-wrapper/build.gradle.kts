plugins {
    kotlin("jvm")
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
    implementation("org.apache.poi:poi-ooxml:5.3.0")

    implementation(project(":common"))

    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}