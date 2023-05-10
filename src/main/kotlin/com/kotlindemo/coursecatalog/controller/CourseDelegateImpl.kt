package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.generated.api.dto.CourseDto
import com.kotlindemo.coursecatalog.generated.controller.CourseDelegate
import com.kotlindemo.coursecatalog.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CourseDelegateImpl(private val courseService: CourseService): CourseDelegate {

    override fun retrieve(courseName: String?): ResponseEntity<List<CourseDto>> {
        return ResponseEntity(courseService.retrieveAll(courseName),HttpStatus.OK)
    }

    override fun add(courseDto: CourseDto): ResponseEntity<CourseDto> {
        return ResponseEntity(courseService.add(courseDto),HttpStatus.CREATED)
    }

    override fun change(courseId: Int, courseDto: CourseDto): ResponseEntity<CourseDto> {
        return ResponseEntity(courseService.change(courseId, courseDto),HttpStatus.OK)
    }

    override fun delete(courseId: Int): ResponseEntity<Unit> {
        return ResponseEntity(courseService.delete(courseId),HttpStatus.NO_CONTENT)
    }
}