package com.kotlindemo.coursecatalog.repository

import com.kotlindemo.coursecatalog.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course,Int> {
    fun findCoursesByNameContaining(courseName: String): List<Course>
    @Query("SELECT * FROM COURSES WHERE CATEGORY LIKE %?1%", nativeQuery = true)
    fun findCoursesByCategoryContaining(courseCategory: String): List<Course>
}