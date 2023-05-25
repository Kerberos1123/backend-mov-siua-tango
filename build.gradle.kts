import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.10"
	kotlin("kapt") version "1.7.20"
}

group = "com.backend-mov-siua-tango"
version = "0.0.1-SNAPSHOT"
//java.sourceCompatibility = JavaVersion.VERSION_17
java.sourceCompatibility = JavaVersion.VERSION_1_8


repositories {
	mavenCentral()
}

dependencies {
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation ("org.mapstruct:mapstruct:1.5.2.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")
	annotationProcessor ("org.mapstruct:mapstruct:1.5.2.Final")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.4")
	implementation("org.springframework.boot:spring-boot-starter-web:2.7.4")
	implementation("org.springframework.boot:spring-boot-starter-security:2.7.4")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.20")
	developmentOnly("org.springframework.boot:spring-boot-devtools:2.7.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.4")
	testImplementation("org.springframework.security:spring-security-test:5.7.3")
	kapt("org.springframework.boot:spring-boot-configuration-processor:2.7.4")
	runtimeOnly("org.postgresql:postgresql:42.5.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
