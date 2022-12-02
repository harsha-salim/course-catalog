package com.kotlindemo.coursecatalog.repository

import com.kotlindemo.coursecatalog.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course,Int> {
}