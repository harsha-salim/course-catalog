package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebClient
class CourseControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    @Test
    fun retrieveAll(){
        val expectedCourseDTOList = mutableListOf(
            CourseDTO(1,"Plants","Science"),
            CourseDTO(2,"Numbers","Maths"),
        )
        every { courseService.retrieveAll() }.returnsMany(expectedCourseDTOList)

        val actualCourseDTOList = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(expectedCourseDTOList,actualCourseDTOList)
    }
    @Test
    fun add(){
        val requestJson = "{\n \"name\":\"Plants\",\n \"category\":\"Science\"\n}"
        val expectedCourseDTO = CourseDTO(1,"Plants","Science")

        every { courseService.add(any()) } returns expectedCourseDTO

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(requestJson)
            .header("Content-Type","application/json")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(CourseDTO::class.java)
            .returnResult()

        Assertions.assertEquals(expectedCourseDTO,response.responseBody)
    }

    @Test
    fun shouldNotAllowCourseWithEmptyNameAndCategoryToBeAdded(){
        val requestJson = "{\n \"name\":\"\",\n \"category\":\"\"\n}"
        val expectedCourseDTO = CourseDTO(1,"Plants","Science")

        every { courseService.add(any()) } returns expectedCourseDTO

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(requestJson)
            .header("Content-Type","application/json")
            .exchange()
            .expectStatus().isBadRequest

    }

    @Test
    fun change(){
        val courseId = 1
        val changes = CourseDTO(courseId,"Plants & Animals","Science")

        every { courseService.change(any(),any()) } returns changes

        val changedDTO = webTestClient
            .put()
            .uri("/v1/courses/{course-id}",courseId)
            .bodyValue(changes)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(changes,changedDTO)
    }

    @Test
    fun delete(){
        val courseId = 1

        every { courseService.delete(any()) } just runs

        webTestClient
            .delete()
            .uri("/v1/courses/{course-id}",courseId)
            .exchange()
            .expectStatus().is2xxSuccessful

    }

}