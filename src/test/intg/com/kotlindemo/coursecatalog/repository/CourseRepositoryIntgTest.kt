package com.kotlindemo.coursecatalog.repository

import com.kotlindemo.coursecatalog.entity.Course
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {
    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setup(){
        courseRepository.deleteAll()
        val course1 = Course(null,"Plants","Science")
        val course2 = Course(null,"Plants & Animals","Science")

        courseRepository.save(course1)
        courseRepository.save(course2)
    }

    @Test
    fun findCourseByNameContaining(){
        val courseName = "Plants"

        val courses: List<Course> = courseRepository.findCoursesByNameContaining(courseName)

        Assertions.assertEquals(2,courses.size)
    }

    @Test
    fun findCourseByCategoryContaining(){
        val categoryName = "Science"

        val courses: List<Course> = courseRepository.findCoursesByCategoryContaining(categoryName)

        Assertions.assertEquals(2,courses.size)
    }
}