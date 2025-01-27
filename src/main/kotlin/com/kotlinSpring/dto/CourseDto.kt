package com.kotlinSpring.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDto(
    val id :Int?,
    @get:NotBlank(message = "courseDTo.name must not be blank")
    val name :String,
    @get:NotBlank(message = "courseDTo.category must not be blank")
    val category: String,
    @get:NotNull(message = "courseDTo.instructorId must not be null")
    val instructorId :Int?= null,
)
