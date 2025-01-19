package com.kotlinSpring.controller

import com.kotlinSpring.dto.CourseDto
import com.kotlinSpring.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDto: CourseDto): CourseDto{

        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun retrieveAllCourses(): List<CourseDto> = courseService.retrieveAllCourses()



    @PutMapping("/{course_id}")
    fun updateCourse(@RequestBody courseDto: CourseDto, @PathVariable("course_id") courseId: Int)= courseService.updateCourse(courseId,courseDto)

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id")courseId: Int)= courseService.deleteCourse(courseId)
}