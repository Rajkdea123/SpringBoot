package com.kotlinspring.controller

import com.kotlinSpring.Entity.course
import com.kotlinSpring.dto.CourseDto
import com.kotlinSpring.repository.CourseRepository
import com.kotlinSpring.repository.InstructorRepository
import com.kotlinspring.util.courseEntityList
import com.kotlinspring.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient

class CourseControllerIntgTest {
    @Autowired
    lateinit var  webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository
    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp(){
         courseRepository.deleteAll()
        instructorRepository.deleteAll()
       val instructor= instructorEntity()
        instructorRepository.save(instructor)
       val courses= courseEntityList(instructor)
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse(){

     val instructor=   instructorRepository.findAll().first()
        val courseDto= CourseDto(null,"Build Restful APIs Using SpringBoot and Kotlin","Dilip",instructor.id)

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
    fun retrieveAllCourses(){
        val courseDTOs= webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        println("courseDto : $courseDTOs")
        assertEquals(3,courseDTOs!!.size)
    }

    @Test
    fun retrieveAllCourses_Byname(){

      val uri=  UriComponentsBuilder.fromUriString("/v1/courses")
            .queryParam("course_name", "SpringBoot")
            .toUriString()
        val courseDTOs= webTestClient
            .get()
            .uri(uri)
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
        val instructor=   instructorRepository.findAll().first()

        val courses= course(null,"Build Restful Apis using SpringBoot and Kotlin","Development",instructor)
            courseRepository.save(courses)
        //courseId
        //Updated CouseDTO
        val updatedCourseDto= CourseDto(null,"Build Restful Apis using SpringBoot and Kotlin","Development",courses.instructor!!.id)
        val updatedCourse= webTestClient
            .put()
            .uri("/v1/courses/{courseId}",courses.id)
            .bodyValue(updatedCourseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody
        assertEquals("Build Restful Apis using SpringBoot and Kotlin",updatedCourse!!.name)
    }


    @Test
    fun deleteCourse(){
        //existing course
        val instructor=   instructorRepository.findAll().first()

        val courses= course(null,"Build Restful Apis using SpringBoot and Kotlin","Development",instructor)
        courseRepository.save(courses)
        //courseId
        //Updated CouseDTO

        val updatedCourse= webTestClient
            .delete()
            .uri("/v1/courses/{courseId}",courses.id)
            .exchange()
            .expectStatus().isNoContent
    }
}