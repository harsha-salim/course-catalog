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
}