package io.github.basic.coro

import io.kotest.core.spec.style.*
import kotlinx.coroutines.*

class ExceptionTest : FunSpec({


    test("测试协程的异常栈丢失") {
        try {
            runBlocking {
                fun3()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
})

suspend fun fun1() {
    delay(100)
    throw Exception("exception at ${System.currentTimeMillis()}")
}

suspend fun fun2() {
    fun1()
    delay(100)
}

suspend fun fun3() {
    fun2()
    delay(100)
}