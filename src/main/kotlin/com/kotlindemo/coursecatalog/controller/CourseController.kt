package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(val courseService: CourseService) {

    @GetMapping
    fun retrieveAll(): List<CourseDTO> {
        return courseService.retrieveAll()
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody @Valid courseDTO: CourseDTO): CourseDTO {
        return courseService.add(courseDTO);
    }
    @PutMapping("/{course-id}")
    fun change(@PathVariable("course-id") courseId: Int,@RequestBody @Valid courseDTO: CourseDTO): CourseDTO {
        return courseService.change(courseId, courseDTO)
    }
    @DeleteMapping("/{course-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("course-id") courseId: Int) {
        courseService.delete(courseId)
    }
}