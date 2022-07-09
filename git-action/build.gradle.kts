
@Suppress("DSL_SCOPE_VIOLATION")
plugins{
    alias(libs.plugins.spring)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.dependency)
    kotlin("plugin.jpa") version "1.6.20"
}

dependencies{
    implementation("mysql:mysql-connector-java:8.0.29")
    testImplementation(rootProject.libs.bundles.springTest)
    implementation(rootProject.libs.spring.boot.starter.web)
    implementation("com.geniuspros.robot.commons:bimini-web-spring-boot-starter:latest.integration")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-neo4j")
}

tasks.bootJar{
    this.archiveFileName.set("git-action.jar")
}

tasks.test{
    useJUnitPlatform()
}