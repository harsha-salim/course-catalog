package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.generated.api.dto.CourseDto
import com.kotlindemo.coursecatalog.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

class CourseDelegateImplUnitTest {

    @MockkBean
    var courseService= mockk<CourseService>()

    private var courseDelegateImpl = CourseDelegateImpl(courseService)

    @Test
    fun retrieveAll(){
        val expectedCourseDTOList = mutableListOf(
            CourseDto(id = 1, name = "Plants", category = "Science", instructorId = 1),
            CourseDto(id = 2, name = "Numbers", category = "Maths", instructorId = 1),
        )
        every { courseService.retrieveAll(any()) }.returnsMany(expectedCourseDTOList)

        val actualCourseDTOList = courseDelegateImpl.retrieve(null).body

        Assertions.assertEquals(expectedCourseDTOList,actualCourseDTOList)
    }

    @Test
    fun retrieve(){
        val courseName = "Plants"
        val expectedCourseDTOList = listOf(CourseDto(id=1,name="Plants", category = "Science", instructorId = 1))
        every { courseService.retrieveAll(any()) } returns expectedCourseDTOList

        val actualCourseDTOList = courseDelegateImpl.retrieve(courseName).body

        Assertions.assertEquals(expectedCourseDTOList,actualCourseDTOList)
    }
    @Test
    fun add(){
        val requestCourse = CourseDto(name = "Plants", category = "Science", instructorId = 1)
        val expectedCourseDTO = CourseDto(id = 1, name = "Plants", category = "Science", instructorId = 1)
        every { courseService.add(any()) } returns expectedCourseDTO

        val response = courseDelegateImpl.add(requestCourse).body

        Assertions.assertEquals(expectedCourseDTO,response)
    }

    @Test
    fun shouldHandleRuntimeException(){
        val requestCourse = CourseDto(name = "Plants", category = "Science", instructorId = 1)
        val message = "Something went wrong :/"
        every { courseService.add(any()) } throws RuntimeException(message)

        val response = assertThrows<RuntimeException> {
            courseDelegateImpl.add(requestCourse).body
        }

        Assertions.assertEquals(message, response.message)
    }

    @Test
    fun change(){
        val courseId = 1
        val changes = CourseDto(id = courseId, name = "Plants & Animals", category = "Science", instructorId = 1)
        every { courseService.change(any(),any()) } returns changes

        val changedDTO = courseDelegateImpl.change(courseId,changes).body

        Assertions.assertEquals(changes,changedDTO)
    }

    @Test
    fun delete(){
        val courseId = 1
        every { courseService.delete(any()) } just runs

        val response = courseDelegateImpl.delete(courseId)

        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.statusCode)

    }

}