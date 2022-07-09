@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    application
    id("io.freefair.aspectj") version "6.5.0.2"
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.kotlin.spring)
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop
    implementation("org.springframework:spring-aspects:5.3.21")
    // https://mvnrepository.com/artifact/org.aspectj/aspectjrt
    // https://mvnrepository.com/artifact/org.springframework/spring-instrument
    implementation("org.springframework:spring-instrument:5.3.21")


}
tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "11"
    }
}
application{
    applicationDefaultJvmArgs = listOf("-javaagent:/Users/taozhiwei/study/test-program/akka/spring-instrument.jar")
}

tasks.test {
    useJUnitPlatform()
}