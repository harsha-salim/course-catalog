package com.kotlindemo.coursecatalog.service

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.entity.Course
import com.kotlindemo.coursecatalog.repository.CourseRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CourseServiceUnitTest {

    @MockkBean
    var courseRepositoryMock= mockk<CourseRepository>()

    @InjectMockKs
    lateinit var courseService:CourseService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        courseService = CourseService(courseRepositoryMock)
    }

    @Test
    fun addCourse(){
        val courseDTO = CourseDTO(null,"plants","science")
        val expectedCourseDTO = CourseDTO(null,"plants","science")

        every { courseRepositoryMock.save(any()) } returns Course(1,"plants","science")

        val actualCourseDTO = courseService.add(courseDTO)

        Assertions.assertTrue(expectedCourseDTO==actualCourseDTO)
    }
}