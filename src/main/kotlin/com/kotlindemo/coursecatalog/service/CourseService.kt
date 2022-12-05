package com.kotlindemo.coursecatalog.service

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.entity.Course
import com.kotlindemo.coursecatalog.exception.CourseNotFoundException
import com.kotlindemo.coursecatalog.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {
    companion object: KLogging()

    fun add(courseDTO : CourseDTO): CourseDTO{
        var courseEntity = courseDTO.let {
            Course(null, it.name, it.category)
        }
        courseRepository.save(courseEntity)
        logger.info("Saved course is : $courseEntity")
        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    fun retrieveAll() : List<CourseDTO> {
        return courseRepository
            .findAll()
            .map {
                CourseDTO(it.id, it.name, it.category)
            }
            .toList()
    }

    fun change(courseId : Int, courseDTO: CourseDTO): CourseDTO {
        var existingCourse = courseRepository.findById(courseId)
        return if (existingCourse.isPresent){
            existingCourse
                .get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    courseRepository.save(it)
                    CourseDTO(it.id, it.name, it.category)
                }
        } else {
            throw CourseNotFoundException("Course with course-id : $courseId is not found")
        }
    }
}