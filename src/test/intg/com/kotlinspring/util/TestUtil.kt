package com.kotlinspring.util

import com.kotlinSpring.Entity.course

fun courseEntityList() = listOf(
    course(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development"),
    course(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,
    ),
    course(null,
        "Wiremock for Java Developers", "Development" ,
    )
)