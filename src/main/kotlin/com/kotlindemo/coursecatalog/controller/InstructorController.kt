package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.InstructorDTO
import com.kotlindemo.coursecatalog.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(val instructorService: InstructorService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addInstructor(@RequestBody @Valid instructorDTO: InstructorDTO) = instructorService.addInstructor(instructorDTO)
}