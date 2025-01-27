package com.kotlinSpring.service

import com.kotlinSpring.Entity.course
import com.kotlinSpring.dto.CourseDto
import com.kotlinSpring.exception.CourseNotFoundException
import com.kotlinSpring.exception.InstructorNotValidException
import com.kotlinSpring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service


@Service
class CourseService(val courseRepository: CourseRepository,
    val instructorService: InstructorService) {

    companion object :KLogging()
    fun addCourse(courseDto: CourseDto) : CourseDto{

      val instructorOptional=  instructorService.findByInstructorId(courseDto.instructorId!!)
        if(!instructorOptional.isPresent){
            throw  InstructorNotValidException("Instructor Not valid for the Id: ${courseDto.instructorId}")
        }

       val courseEntity= courseDto.let {
            course(null,it.name,it.category,instructorOptional.get())
        }
        courseRepository.save(courseEntity)

        logger.info("Saved course is : $courseEntity")

        return courseEntity.let {
            CourseDto(it.id,it.name,it.category, it.instructor!!.id)
        }
    }

    fun retrieveAllCourses(courseName: String?): List<CourseDto> {

       val courses= courseName?.let {
            courseRepository.findCoursebyName(courseName)

        } ?: courseRepository.findAll()

      return courses
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
