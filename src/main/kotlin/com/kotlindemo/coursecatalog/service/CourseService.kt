package com.kotlindemo.coursecatalog.service

import com.kotlindemo.coursecatalog.entity.Course
import com.kotlindemo.coursecatalog.exception.CourseNotFoundException
import com.kotlindemo.coursecatalog.exception.InstructorNotValidException
import com.kotlindemo.coursecatalog.generated.api.dto.CourseDto
import com.kotlindemo.coursecatalog.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository, val instructorService: InstructorService) {
    companion object: KLogging()

    fun add(courseDTO : CourseDto): CourseDto{
        val instructorOptional = instructorService.findByInstructorId(courseDTO.instructorId)
        if (!instructorOptional.isPresent){
            throw InstructorNotValidException("Instructor not valid for id: ${courseDTO.instructorId}")
        }

        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category, instructorOptional.get())
        }

        courseRepository.save(courseEntity)
        logger.info("Saved course is : $courseEntity")
        return courseEntity.let {
            CourseDto(id=it.id, name=it.name, category = it.category, instructorId = it.instructor!!.id!!)
        }
    }

    fun retrieveAll(courseName: String?) : List<CourseDto> {
        val courses = courseName?.let {
            courseRepository.findCoursesByNameContaining(courseName)
        }
            ?: courseRepository.findAll()
        return courses
            .map {
                CourseDto(id=it.id, name = it.name, category = it.category, instructorId = it.instructor!!.id!!)
            }
            .toList()
    }

    fun change(courseId : Int, courseDTO: CourseDto): CourseDto {
        val existingCourse = courseRepository.findById(courseId)
        return if (existingCourse.isPresent){
            existingCourse
                .get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    courseRepository.save(it)
                    CourseDto(id = it.id, name = it.name, category = it.category, instructorId = courseDTO.instructorId)
                }
        } else {
            throw CourseNotFoundException("Course with course-id : $courseId is not found")
        }
    }

    fun delete(courseId: Int){
        val existingCourse = courseRepository.findById(courseId)
        return if (existingCourse.isPresent){
            existingCourse
                .get()
                .let {
                    courseRepository.deleteById(courseId)
                }
        } else {
            throw CourseNotFoundException("Course with course-id : $courseId is not found")
        }
    }
}