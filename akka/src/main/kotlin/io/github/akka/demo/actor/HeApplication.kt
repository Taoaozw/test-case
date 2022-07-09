package io.github.akka.demo.actor

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.event.*
import org.springframework.cglib.core.*
import org.springframework.context.annotation.*
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.*

@EnableAsync(mode = AdviceMode.ASPECTJ)
@SpringBootApplication
class HeApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/taozhiwei/study/test-program")
            runApplication<HeApplication>(*args)
        }
    }


    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent(event: ApplicationReadyEvent) {
        val beansOfType = event.applicationContext.getBeansOfType(TestService::class.java)
        val bean = event.applicationContext.getBean(TestService::class.java)
        beansOfType.forEach { (key, value) ->
            println("key: $key, value: $value")
        }
    }
}


