@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("io.freefair.aspectj") version "6.5.0.2"
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.kotlin.spring)
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
}
tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "11"
    }
}
tasks.test{
    useJUnitPlatform()
}