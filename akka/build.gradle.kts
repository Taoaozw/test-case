@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.kotlin.spring)
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop
    implementation("org.springframework:spring-aspects:5.3.21")


}
tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.bootJar{

}

tasks.test {
    useJUnitPlatform()
}