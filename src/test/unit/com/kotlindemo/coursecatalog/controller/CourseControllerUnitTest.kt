package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
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
        var expectedCourseDTOList = mutableListOf<CourseDTO>(
            CourseDTO(1,"Plants","Science"),
            CourseDTO(2,"Numbers","Maths"),
        )
        every { courseService.retrieveAll() } returns expectedCourseDTOList

        var actualCourseDTOList = webTestClient
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
        var requestJson = "{\n \"name\":\"Plants\",\n \"category\":\"Science\"\n}"
        var expectedCourseDTO = CourseDTO(1,"Plants","Science")

        every { courseService.add(any()) } returns expectedCourseDTO

        var response = webTestClient
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
    fun change(){
        val courseId = 1
        val changes = CourseDTO(1,"Plants & Animals","Science")

        every { courseService.change(any(),any()) } returns changes

        var changedDTO = webTestClient
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

}