package com.kotlindemo.coursecatalog.service

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.entity.Course
import com.kotlindemo.coursecatalog.repository.CourseRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

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
    fun add(){
        val courseDTO = CourseDTO(null,"plants","science")
        val expectedCourseDTO = CourseDTO(null,"plants","science")

        every { courseRepositoryMock.save(any()) } returns Course(1,"plants","science")

        val actualCourseDTO = courseService.add(courseDTO)

        Assertions.assertTrue(expectedCourseDTO==actualCourseDTO)
    }

    @Test
    fun retrieveAll(){

        val coursesList = mutableListOf(
            Course(1,"Plants","Science"),
            Course(2,"Numbers","Maths"),
        )
        every { courseRepositoryMock.findAll() } returns coursesList

        val expectedCourseDTOList = mutableListOf(
            CourseDTO(1,"Plants","Science"),
            CourseDTO(2,"Numbers","Maths"),
        )

        val actualCourseDTOList = courseService.retrieveAll()

        Assertions.assertTrue(actualCourseDTOList.containsAll(expectedCourseDTOList))
    }

    @Test
    fun change(){
        val courseId = 1
        val changes = CourseDTO(1,"Plants & Animals","Science")

        every { courseRepositoryMock.findById(any()) } returns Optional.of(Course(1,"Plants","Science"))
        every { courseRepositoryMock.save(any()) } returns Course(1,"Plants & Animals","Science")

        val resultCourseDTO = courseService.change(courseId,changes)

        Assertions.assertEquals(changes,resultCourseDTO)
    }

    @Test
    fun delete(){
        val courseId = 1
        every { courseRepositoryMock.findById(any()) } returns Optional.of(Course(1,"Plants","Science"))
        every { courseRepositoryMock.deleteById(any()) } just runs

        Assertions.assertDoesNotThrow { courseService.delete(courseId) }
    }
}