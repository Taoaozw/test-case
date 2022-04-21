import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    java
    kotlin("jvm") version "1.6.20-RC2"
}


repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    group = "io.github"
    version = "1.0-SNAPSHOT"

    apply(plugin = "kotlin")

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
        implementation(rootProject.libs.kt.log)
        testImplementation(rootProject.libs.bundles.test)
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }


}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}