package com.kotlinSpring.repository

import com.kotlinSpring.Entity.course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<course, Int> {

   fun findByNameContaining(courseName: String) : List<course>

   @Query(value= "SELECT * FROM COURSES where name like %?%", nativeQuery = true)
   fun findCoursebyName(courseName: String): List<course>


}