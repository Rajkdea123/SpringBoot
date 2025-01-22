package com.kotlinSpring.repository

import com.kotlinSpring.Entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor, Int> {
}