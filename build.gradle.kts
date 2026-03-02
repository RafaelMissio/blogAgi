plugins {
    id("java");
    id("io.qameta.allure") version "2.12.0"
}

group = "br.com.missio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")
    implementation("io.github.bonigarcia:webdrivermanager:6.3.3")
    testImplementation("com.codeborne:selenide:7.6.0")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")

    testImplementation("io.qameta.allure:allure-junit5:2.25.0")

}

tasks.test {
    useJUnitPlatform()
}

allure {
    version = "2.32.0"
    adapter {
        autoconfigure = true
        aspectjWeaver = true
    }
}