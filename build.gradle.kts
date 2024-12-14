import java.net.URI

plugins {
    kotlin("jvm") version "2.1.0"
    `maven-publish`
}

group = "com.geronso"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = URI.create("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.geronso"
            artifactId = "dragonfly-algorithm"
            version = "1.0"

            from(components["java"])
        }
    }
}