plugins {
    id("java")
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

}

tasks.test {
    useJUnitPlatform()
}