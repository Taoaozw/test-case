package io.github.aop.demo

import org.springframework.context.annotation.EnableLoadTimeWeaving
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*

@RestController
@EnableLoadTimeWeaving
@RequestMapping("/test")
class TestController(
    private val testService: TestService
) {


    @GetMapping("/get")
    fun test1() = testService.hi()


}


@Service
class TestService {

    fun hello() = "hello"

    fun hi() = hello()

}