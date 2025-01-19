package com.kotlinSpring.repository

import com.kotlinSpring.Entity.course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<course, Int> {


}