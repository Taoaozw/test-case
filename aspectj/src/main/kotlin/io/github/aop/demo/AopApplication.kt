package io.github.aop.demo

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*

@SpringBootApplication
class AopApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<AopApplication>(*args)
        }
    }
}