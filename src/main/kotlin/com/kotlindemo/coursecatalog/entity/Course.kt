package com.kotlindemo.coursecatalog.entity

import javax.persistence.*

@Entity
@Table(name = "courses")
data class Course (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Int?,
    var name : String,
    var category : String
)