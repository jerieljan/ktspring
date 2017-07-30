package com.jerieljan.ktspring.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController {

    @GetMapping("/")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello world!")
    }
}
