package io.github.akka.demo.actor

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.*
import org.springframework.web.method.annotation.*


@RestControllerAdvice
class Hadnler {


    /**
     * 处理 SpringMVC 请求参数类型错误
     *
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchExceptionHandler(
        ex: MethodArgumentTypeMismatchException,
    ) {
    }
}

@Controller
class Test{
    @RequestMapping("/test")
    fun test() {
        val currentRequestAttributes = RequestContextHolder.currentRequestAttributes()
    }
}