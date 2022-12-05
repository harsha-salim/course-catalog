package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @GetMapping
    fun retrieveAll(): List<CourseDTO> {
        return courseService.retrieveAll()
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.add(courseDTO);
    }
    @PutMapping("/{course-id}")
    fun change(@PathVariable("course-id") courseId: Int,@RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.change(courseId, courseDTO)
    }
}