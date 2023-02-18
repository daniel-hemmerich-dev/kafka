plugins {
    id("java")
    id("application")
}

group = "org.tutorial"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.apache.kafka:kafka-clients:3.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

application {
    mainClassName = "org.tutorial.Main"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}