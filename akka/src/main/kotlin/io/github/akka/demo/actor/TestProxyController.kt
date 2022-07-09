package io.github.akka.demo.actor

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/actor")
class TestProxyController( val service: TestService) {


    @RequestMapping("/test")
    fun test() {
        service.hello()
    }
}