package com.kotlinspring.controller.repository

import com.kotlinSpring.repository.CourseRepository
import com.kotlinspring.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.stream.Stream


@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp(){
        courseRepository.deleteAll()
        val courses= courseEntityList()
        courseRepository.saveAll(courses)
    }
    @Test
    fun findByNameContaining(){

      val courses=  courseRepository.findByNameContaining("SpringBoot")
        print("courses : $courses")

        Assertions.assertEquals(2,courses.size)
    }
    @Test
    fun findCoursebyName(){

        val courses=  courseRepository.findCoursebyName("SpringBoot")
        print("courses : $courses")

        Assertions.assertEquals(2,courses.size)
    }


    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursebyName_approach2(name: String ,expectedSize: Int){

        val courses=  courseRepository.findCoursebyName(name)
        print("courses : $courses")

        Assertions.assertEquals(expectedSize,courses.size)
    }
    companion object{


        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {

            return Stream.of(
                Arguments.arguments("SpringBoot", 2),
                Arguments.arguments("Wiremock", 1)
            )
        }
    }
}