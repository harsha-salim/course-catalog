package com.kotlindemo.coursecatalog.service

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.entity.Course
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
}