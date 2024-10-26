package org.app.courses.controllers

import jakarta.validation.Valid
import org.app.courses.dto.CourseDepartmentDto
import org.app.courses.dto.CourseDto
import org.app.courses.dto.ResponseDto
import org.app.courses.models.Course
import org.app.courses.services.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/courses")
@CrossOrigin("http://localhost:3000")
@PreAuthorize("hasAnyAuthority('courses_role')")
class CoursesController(
 private val courseService: CourseService
) {

    @GetMapping("")
    fun getAllCourses():MutableList<Course>{
        return courseService.getAllCourses()
    }

    @GetMapping("student/{studentId}")
    fun getStudentCourse(
        @PathVariable studentId: String): MutableList<CourseDepartmentDto> {
        return courseService.getStudentCourse(studentId)
    }


    @PostMapping("save")
    fun saveCourse(@RequestBody @Valid courseDto: CourseDto):ResponseEntity<ResponseDto>{
        courseService.saveCourse(courseDto)
        return ResponseEntity.ok(ResponseDto("Course Created"))
    }
}