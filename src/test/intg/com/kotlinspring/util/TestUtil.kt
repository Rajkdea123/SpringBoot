package com.kotlinspring.util

import com.kotlinSpring.Entity.course
import com.kotlinSpring.dto.CourseDto

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
fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APis using Spring Boot and Kotlin",
    category: String = "Development",
   // instructorId: Int? = 1
) = CourseDto(
    id,
    name,
    category,
   // instructorId
)