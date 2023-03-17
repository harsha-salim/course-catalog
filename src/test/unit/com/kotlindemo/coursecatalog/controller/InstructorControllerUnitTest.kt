package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.InstructorDTO
import com.kotlindemo.coursecatalog.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebClient
class InstructorControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorService: InstructorService

    @Test
    fun addInstructor(){
        val instructorDTO = InstructorDTO(null,"John Adams")
        val expectedInstructorDTO = InstructorDTO(1,"John Adams")

        every { instructorService.addInstructor(any()) } returns expectedInstructorDTO

        val response = webTestClient
            .post()
            .uri("/v1/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()

        Assertions.assertNotNull(response.responseBody!!.id)
        Assertions.assertEquals(expectedInstructorDTO,response.responseBody)
    }

    @Test
    fun addInstructor_Validation(){
        val instructorDTO = InstructorDTO(null,"")
        val expectedInstructorDTO = InstructorDTO(1,"John Adams")

        every { instructorService.addInstructor(any()) } returns expectedInstructorDTO

        val response = webTestClient
            .post()
            .uri("/v1/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("instructorDTO.name must not be blank", response.responseBody)
    }

}