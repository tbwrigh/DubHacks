plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25" // Add the JPA plugin for Kotlin
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "dev.tbwright"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot and Kotlin dependencies
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web") // For REST controllers
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") // For JPA/Hibernate
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // For Kotlin JSON serialization/deserialization
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")  // For parsing JWT

	runtimeOnly("org.postgresql:postgresql")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	jvmToolchain(21)  // This will align Kotlin with Java 21
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	mainClass.set("dev.tbwright.dubhacks.DubhacksApplication")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
