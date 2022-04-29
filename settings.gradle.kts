rootProject.name = "test-program"
include("basic")
include("git-action")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("spring-cloud", "2021.0.1")
            version("vertx", "4.2.6")
            version("kotlin", "1.6.20")
            version("kotlin-logging", "2+")
            version("jackson") {
                strictly("2.13.+")
                reject("2.13.2.1")
            }
            version("logback", "1.2.10")
            version("ktoml", "0.2.11")
            version("pb-plugin", "0.8.+")
            version("spring", "2.6.+")
            version("coroutines", "1.6.+")

            version("kotest", "5.+")
            version("kotest-ext-spring", "1.1.+")
            version("junit", "5.+")
            version("protobuf") {
                strictly("[3.19, 3.20[")
                prefer("3.19.4")
            }


            library("vertx-core", "io.vertx", "vertx-core").versionRef("vertx")
            library("vertx-mqtt", "io.vertx", "vertx-mqtt").versionRef("vertx")
            library("vertx-kt", "io.vertx", "vertx-lang-kotlin").versionRef("vertx")
            library("vertx-kt-coroutines", "io.vertx", "vertx-lang-kotlin-coroutines").versionRef("vertx")


            library("jackson-core", "com.fasterxml.jackson.core", "jackson-core").versionRef("jackson")
            library("jackson-databind", "com.fasterxml.jackson.core", "jackson-databind").versionRef("jackson")
            library("jackson.annotations", "com.fasterxml.jackson.core", "jackson-annotations").versionRef("jackson")
            library("jackson-jsr310", "com.fasterxml.jackson.datatype", "jackson-datatype-jsr310").versionRef("jackson")


            library("kt-log", "io.github.microutils", "kotlin-logging-jvm").versionRef("kotlin-logging")

            library("logback-core", "ch.qos.logback", "logback-core").versionRef("logback")
            library("logback-classic", "ch.qos.logback", "logback-classic").versionRef("logback")


            library("coroutines-core-jvm", "org.jetbrains.kotlinx", "kotlinx-coroutines-core-jvm").versionRef("coroutines")
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            library("coroutines-test", "org.jetbrains.kotlinx", "kotlinx-coroutines-test").versionRef("coroutines")

            library("toml-parser-core", "com.akuleshov7", "ktoml-core").versionRef("ktoml")
            library("toml-parser-file", "com.akuleshov7", "ktoml-file").versionRef("ktoml")


            library("spring-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library("spring-context", "org.springframework", "spring-context").withoutVersion()
            library("kotest-spring", "io.kotest.extensions", "kotest-extensions-spring").versionRef("kotest-ext-spring")
            library("kotest-assertions", "io.kotest", "kotest-assertions-core").versionRef("kotest")
            library("kotest-junit5", "io.kotest", "kotest-runner-junit5").versionRef("kotest")
            library("kotest-property", "io.kotest", "kotest-property").versionRef("kotest")
            library("junit-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit")
            library("junit-enginer", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")

            library("protobuf-java", "com.google.protobuf", "protobuf-java").versionRef("protobuf")
            library("protobuf-kotlin", "com.google.protobuf", "protobuf-kotlin").versionRef("protobuf")
            library("protobuf-java-util", "com.google.protobuf", "protobuf-java-util").versionRef("protobuf")



            //spring
            library("spring-boot-starter-aop", "org.springframework.boot", "spring-boot-starter-aop").withoutVersion()
            library("spring-boot-starter-actuator", "org.springframework.boot", "spring-boot-starter-actuator").withoutVersion()
            library("spring-boot-starter-jooq", "org.springframework.boot", "spring-boot-starter-jooq").withoutVersion()
            library("spring-boot-starter-jdbc", "org.springframework.boot", "spring-boot-starter-jdbc").withoutVersion()
            library("spring-boot-starter-validation", "org.springframework.boot", "spring-boot-starter-validation").withoutVersion()
            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").withoutVersion()
            library("spring-boot-starter", "org.springframework.boot", "spring-boot-starter").withoutVersion()
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library("spring-feign", "org.springframework.cloud", "spring-cloud-starter-openfeign").withoutVersion()
            library("spring-feign-httpclient", "io.github.openfeign", "feign-httpclient").version("latest.release")
            library("spring-feign-okhttp", "io.github.openfeign", "feign-okhttp").version("latest.release")
            library("spring-boot-starter-redis", "org.springframework.boot", "spring-boot-starter-data-redis").withoutVersion()

            bundle(
                "test",
                listOf(
                    "kotest-junit5", "kotest-assertions", "kotest-property", "junit-enginer", "coroutines-core",
                    "coroutines-core-jvm", "coroutines-test"
                )
            )
            bundle(
                "springTest", listOf(
                    "junit-api", "kotest-spring", "kotest-junit5", "kotest-assertions", "kotest-property",
                    "junit-enginer", "spring-test", "coroutines-core", "coroutines-core-jvm", "coroutines-test"
                )
            )


            bundle("vertxMq", listOf("vertx-core", "vertx-mqtt", "vertx-kt", "vertx-kt-coroutines"))
            bundle("logback", listOf("logback-core", "logback-classic"))
            bundle("jackson", listOf("jackson-core", "jackson-databind", "jackson.annotations", "jackson-jsr310"))
            bundle("ktoml", listOf("toml-parser-core", "toml-parser-file"))
            bundle("protobuf", listOf("protobuf-java", "protobuf-kotlin", "protobuf-java-util"))


            plugin("protobuf", "com.google.protobuf").versionRef("pb-plugin")
            plugin("kotlin-spring", "org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")
            plugin("kotlin-plugin-allopen", "org.jetbrains.kotlin.plugin.allopen").versionRef("kotlin")
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-kapt", "org.jetbrains.kotlin.kapt").versionRef("kotlin")
            plugin("spring", "org.springframework.boot").versionRef("spring")
            plugin("spring.dependency", "io.spring.dependency-management").version("latest.release")
        }
    }
}
