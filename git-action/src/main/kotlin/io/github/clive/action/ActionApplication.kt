package io.github.clive.action

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@SpringBootApplication
class ActionApplication {

    @RequestMapping("/test/1")
    fun test() = Optional.empty<Int>()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ActionApplication::class.java, *args)
        }
    }

}

