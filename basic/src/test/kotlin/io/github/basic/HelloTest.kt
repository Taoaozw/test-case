package io.github.basic

import io.kotest.core.spec.style.*
import java.lang.invoke.*

class HelloTest : FunSpec({

    beforeTest {
    }

    afterTest { (testCase, result) ->
    }

    test("MethodHandler") {
        val lookup = MethodHandles.lookup()

    }

})


class Test(val name: String, val age: Int) {
    fun test() {
        println("test")
    }
}
