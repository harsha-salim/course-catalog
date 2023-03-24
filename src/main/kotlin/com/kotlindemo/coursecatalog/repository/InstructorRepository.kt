package com.kotlindemo.coursecatalog.repository

import com.kotlindemo.coursecatalog.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository: CrudRepository<Instructor,Int>