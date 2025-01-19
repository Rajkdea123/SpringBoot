package com.kotlinSpring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CourseCatlogServiceApplication

fun main(args: Array<String>) {
	runApplication<CourseCatlogServiceApplication>(*args)
}
