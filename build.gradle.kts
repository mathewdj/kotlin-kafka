val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven ("https://packages.confluent.io/maven")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.apache.kafka:kafka-clients:7.0.1-ccs")
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.test {
    exclude("**/*IntegrationTest.class")
    useJUnitPlatform()

    testLogging {
        events("PASSED", "SKIPPED", "FAILED")
    }
}