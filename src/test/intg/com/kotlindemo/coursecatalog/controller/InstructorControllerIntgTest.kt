package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.InstructorDTO
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
class InstructorControllerIntgTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun addInstructor(){
        val requestJson = "{\n \"id\":null,\n \"name\":\"John Adam\"\n}"

        var response = webTestClient
            .post()
            .uri("/v1/instructors/")
            .bodyValue(requestJson)
            .header("Content-Type","application/json")
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()

        Assertions.assertNotNull(response.responseBody!!.id)
    }
}