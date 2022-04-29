package io.github.clive.action.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct


@RestController
@RequestMapping("/api/six")
class ApiSixController {

    @PostConstruct
    fun init() {
        println("ApiSixController init")
    }

    @GetMapping("/blog/{id}/name")
    fun getBlogName(@PathVariable id: Int): String {
        return "Blog $id"
    }


    @GetMapping("/blogs/-")
    fun listBlogs(): List<String> {
        return listOf("Blog 1", "Blog 2", "Blog 3")
    }

}