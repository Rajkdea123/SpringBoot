package com.kotlinspring.controller

import com.kotlinSpring.Entity.course
import com.kotlinSpring.controller.CourseController
import com.kotlinSpring.dto.CourseDto
import com.kotlinSpring.service.CourseService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient

class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMockk: CourseService

    @Test
    fun addCourse(){
        val courseDto= CourseDto(null,"Build Restful APIs Using SpringBoot and Kotlin","Dilip")

        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id=1)
        val savedCourseDto= webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody
        Assertions.assertTrue{
            savedCourseDto!!.id!= null
        }
    }

    @Test
    fun addCourse_validation(){
        val courseDto= CourseDto(null,"","")

        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id=1)
        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("courseDto.category must not be blank, courseDto.name must not be blank", response)
    }

    @Test
    fun addCourse_runtimeException(){
        val courseDto= CourseDto(null,"Build Restful Apis using SpringBoot and Kotlin","Dillip")

        val errorMessage= "Unexpected Error occured"
        every { courseServiceMockk.addCourse(any()) }  throws  RuntimeException(errorMessage)
        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }

    @Test
    fun retrieveAllCourses(courseName: String?){
        every { courseServiceMockk.retrieveAllCourses(courseName) }.returnsMany(
            listOf(courseDTO(id=1),
                courseDTO(id=2,
                    name = "Build Reactive Microservices using Spring WbFlux/SpringBoot")
            )
        )
        val courseDTOs= webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        println("courseDto : $courseDTOs")
        assertEquals(2,courseDTOs!!.size)
    }
    @Test
    fun updateCourse(){
        //existing course
        val courses= course(null,"Build Restful Apis using SpringBoot and Kotlin","Development")
       every { courseServiceMockk.updateCourse(any(),any()) } returns courseDTO(100, name = "Build Restful Apis using SpringBoot and Kotlin1")
//               courseDTO(100,"Build Restful Apis using SpringBoot and Kotlin","Development")
        //courseId
        //Updated CourseDTO
        val updatedCourseDto= CourseDto(null,"Build Restful Apis using SpringBoot and Kotlin","Development")
        val updatedCourse= webTestClient
            .put()
            .uri("/v1/courses/{courseId}",100)
            .bodyValue(updatedCourseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody
        assertEquals("Build Restful Apis using SpringBoot and Kotlin1",updatedCourse!!.name)
    }

    @Test
    fun deleteCourse(){

        every { courseServiceMockk.deleteCourse(any()) } just runs
        val updatedCourse= webTestClient
            .delete()
            .uri("/v1/courses/{courseId}",100)
            .exchange()
            .expectStatus().isNoContent
    }
}