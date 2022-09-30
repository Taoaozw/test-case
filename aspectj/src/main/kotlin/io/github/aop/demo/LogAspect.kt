package io.github.aop.demo

import org.aspectj.lang.annotation.*
import org.springframework.stereotype.Component


@Aspect
@Component
class LogAspect {

    @Pointcut("execution(* io.github.aop.demo.TestService.hello(..))")
    fun pointCut() {}

    @Before(value = "LogAspect.pointCut()")
    fun beforeAdd() {
        println("this is aop add method")
    }

}