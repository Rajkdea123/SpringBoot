package com.kotlinSpring.service

import com.kotlinSpring.Entity.course
import com.kotlinSpring.dto.CourseDto
import com.kotlinSpring.exception.CourseNotFoundException
import com.kotlinSpring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service


@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object :KLogging()
    fun addCourse(courseDto: CourseDto) : CourseDto{

       val courseEntity= courseDto.let {
            course(null,it.name,it.category)
        }
        courseRepository.save(courseEntity)
        logger.info("Saved course is : $courseEntity")
        return courseEntity.let {
            CourseDto(it.id,it.name,it.category)
        }
    }

    fun retrieveAllCourses(): List<CourseDto> {

      return courseRepository.findAll()
            .map {
                CourseDto(it.id,it.name,it.category)

            }
    }

    fun updateCourse(courseId: Int, courseDto: CourseDto): CourseDto {

       val existingCourse=  courseRepository.findById(courseId)
      return if(existingCourse.isPresent){
            existingCourse.get()
                .let {
                    it.name= courseDto.name
                    it.category= courseDto.category
                    courseRepository.save(it)
                    CourseDto(it.id,it.name,it.category)

                }
        }else{
            throw  CourseNotFoundException("No course found for the passed in Id : $courseId")
        }

    }

    fun deleteCourse(courseId: Int){

        val existingCourse=  courseRepository.findById(courseId)
        if(existingCourse.isPresent){
            existingCourse.get()
                .let {

                    courseRepository.deleteById(courseId)


                }
        }else{
            throw  CourseNotFoundException("No course found for the passed in Id : $courseId")
        }


    }

}
