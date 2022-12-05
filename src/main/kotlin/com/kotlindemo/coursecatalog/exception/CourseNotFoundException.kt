package com.kotlindemo.coursecatalog.exception

class CourseNotFoundException(override val message: String) : RuntimeException() {

}
