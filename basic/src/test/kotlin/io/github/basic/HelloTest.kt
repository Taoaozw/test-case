package io.github.basic

import io.kotest.core.spec.style.*
import kotlinx.coroutines.*
import java.lang.invoke.*

class HelloTest : FunSpec({

    beforeTest {
    }

    afterTest { (testCase, result) ->
    }

    test("MethodHandler") {

    }

})





class Test(val name: String, val age: Int) {
    fun test() {
        println("test")
    }
}
