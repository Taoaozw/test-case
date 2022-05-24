@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.kotlin.spring)
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}