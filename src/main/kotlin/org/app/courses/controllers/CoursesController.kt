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
import org.springframework.data.domain.Page
import java.util.UUID

@RestController
@RequestMapping("api/courses")
@CrossOrigin(origins = ["http://localhost:4200","https://muhohodev.com"])
@PreAuthorize("hasAnyAuthority('course_role')")
class CoursesController(
 private val courseService: CourseService
) {

    @GetMapping("")
    fun getAllCourses(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ):Page<Course>{
        return courseService.getAllCourses(page,size)
    }

    @GetMapping("un-paginated")
    fun getCoursesWithoutPagination():MutableList<Course>{
        return courseService.getUnpaginatedCourses()
    }

    @GetMapping("student/{studentId}")
    fun getStudentCourse(
        @PathVariable studentId: UUID): MutableList<CourseDepartmentDto> {
        return courseService.getStudentCourse(studentId)
    }


    @PostMapping("save")
    fun saveCourse(@RequestBody @Valid courseDto: CourseDto):ResponseEntity<ResponseDto>{
        courseService.saveCourse(courseDto)
        return ResponseEntity.ok(ResponseDto("Course Created"))
    }
}