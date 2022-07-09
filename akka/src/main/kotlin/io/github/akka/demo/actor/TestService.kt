package io.github.akka.demo.actor

import org.springframework.context.*
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.lang.Thread.*


@Service
class TestService : ApplicationContextAware {

    lateinit var context: ApplicationContext


    fun hello() {
        context.getBean(TestService::class.java).naaa()
        //println("sleep start")
            //naaa()
    }

    @Async
    fun naaa() {
        sleep(2000)
        println("sleep end")
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}