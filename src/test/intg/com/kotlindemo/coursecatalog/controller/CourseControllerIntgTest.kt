package com.kotlindemo.coursecatalog.controller

import com.kotlindemo.coursecatalog.dto.CourseDTO
import com.kotlindemo.coursecatalog.entity.Course
import com.kotlindemo.coursecatalog.entity.Instructor
import com.kotlindemo.coursecatalog.repository.CourseRepository
import com.kotlindemo.coursecatalog.repository.InstructorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {
    @Autowired
    lateinit var webTestClient : WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setup(){
        instructorRepository.deleteAll()
        val instructor = Instructor(null,"John")
        instructorRepository.save(instructor)

        courseRepository.deleteAll()
        val courseList = mutableListOf(Course(null,"Plants","Science", instructor))
        courseRepository.saveAll(courseList)
    }

    @Test
    fun retrieve(){
        val courseName = "Plants"

        val uri = UriComponentsBuilder
            .fromUriString("/v1/courses")
            .queryParam("course-name",courseName)
            .toUriString()

        val courseDTOList = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue(courseDTOList?.size==1)
    }

    @Test
    fun retrieveAll(){
        val expectedCourseDTO = CourseDTO(2,"Plants","Science",instructorRepository.findAll().first().id)

        val courseDTOList = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(mutableListOf(expectedCourseDTO),courseDTOList)
    }

    @Test
    fun add(){
        val requestJson = "{\n \"name\":\"Numbers\",\n \"category\":\"Maths\",\n" +
                " \"instructorId\":${instructorRepository.findAll().first().id}\n}"

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(requestJson)
            .header("Content-Type","application/json")
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()

        Assertions.assertNotNull(response.responseBody!!.id)
    }

    @Test
    fun change(){
        val course = Course(null,"Plants Only","Science",instructorRepository.findAll().first())
        courseRepository.save(course)

        val changes = CourseDTO(null,"Plants & Animals","Science",instructorRepository.findAll().first().id)

        val changedDTO = webTestClient
            .put()
            .uri("/v1/courses/{course-id}",course.id)
            .bodyValue(changes)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(changes.name,changedDTO!!.name)
    }

    @Test
    fun delete(){
        val course = Course(null,"Plants","Science",instructorRepository.findAll().first())
        courseRepository.save(course)

        webTestClient
            .delete()
            .uri("/v1/courses/{course-id}",course.id)
            .exchange()
            .expectStatus().isNoContent

    }
}