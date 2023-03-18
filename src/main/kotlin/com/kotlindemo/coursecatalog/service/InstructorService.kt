package com.kotlindemo.coursecatalog.service

import com.kotlindemo.coursecatalog.dto.InstructorDTO
import com.kotlindemo.coursecatalog.entity.Instructor
import com.kotlindemo.coursecatalog.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(val instructorRepository: InstructorRepository) {
    fun addInstructor(instructorDTO: InstructorDTO): InstructorDTO {
        println("-------------------------------------------------------------")
        val instructorEntity = instructorDTO.let {
            Instructor(it.id, it.name)
        }
        instructorRepository.save(instructorEntity)
        return instructorEntity.let {
            InstructorDTO(it.id, it.name)
        }
    }

    fun findByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId)
    }

}
