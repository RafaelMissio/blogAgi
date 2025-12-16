plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

group = "br.com.missio.blogdoagi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val junitVersion = "5.10.2"
val seleniumVersion = "4.23.0"
val allureJunit5Version = "2.27.0"
val webDriverManagerVersion = "6.3.3"
val slf4jVersion = "2.0.16"

dependencies {
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webDriverManagerVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureJunit5Version")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

tasks.withType<Test>().configureEach {

    useJUnitPlatform()
    maxParallelForks = 1

    // 🔐 UTF-8 GARANTIDO NA JVM DOS TESTES
    systemProperty("file.encoding", "UTF-8")
    jvmArgs("-Dfile.encoding=UTF-8")

    // 🔒 Execução sequencial (JUnit 5)
    systemProperty("junit.jupiter.execution.parallel.enabled", "false")
    systemProperty("junit.jupiter.execution.parallel.mode.default", "same_thread")
    systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "same_thread")


    testLogging {
        events(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
        )
    }
}

allure {
    version = "2.32.0"
    adapter {
        autoconfigure = true
        aspectjWeaver = true
    }
}
