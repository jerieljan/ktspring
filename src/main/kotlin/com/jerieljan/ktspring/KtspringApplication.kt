package com.jerieljan.ktspring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KtspringApplication

fun main(args: Array<String>) {
    SpringApplication.run(KtspringApplication::class.java, *args)

}
