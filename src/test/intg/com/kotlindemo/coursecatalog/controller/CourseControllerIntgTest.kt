package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.CourseDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {
    @Autowired
    lateinit var webTestClient : WebTestClient

    @Test
    fun add(){
        var requestJson = "{\n \"name\":\"Plants\",\n \"category\":\"Science\"\n}"
        var expectedCourseDTO = CourseDTO(1,"Plants","Science")

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