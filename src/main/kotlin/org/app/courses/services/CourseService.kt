package org.app.courses.services

import org.app.courses.dto.CourseDepartmentDto
import org.app.courses.dto.CourseDto
import org.app.courses.dto.CourseEnrollmentDto
import org.app.courses.models.Course
import org.app.courses.models.CourseEnrollment
import org.app.courses.repositories.CourseEnrollmentRepository
import org.app.courses.repositories.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val departmentService: DepartmentService,
    private val courseEnrollmentRepository: CourseEnrollmentRepository,
){

    fun getAllCourses():MutableList<Course>{
        return courseRepository.findAll()
    }

    fun saveCourse(courseDto: CourseDto){

        val department = departmentService.getDepartmentById(courseDto.departmentId.toLong())

        println(department.name)
        val course = Course(
            null,
            courseDto.name,
            department
        )
        courseRepository.save(course)
    }

  /*  @KafkaListener(topics = ["students-topic"], groupId = "CourseGroup")
    fun receiveData(userJson:String){
        try {
            val objectMapper = jacksonObjectMapper()
            val student: UserDto = objectMapper.readValue(userJson)
            println("***************** This is Course kafka Consumer *************************")
            println(userJson)
            val courseEnrollmentDto = CourseEnrollmentDto(
                null, student.id.toString(),student.course
            );
            this.enrollStudent(courseEnrollmentDto)
//            println("Consumed student: ${student.name} with admNo: ${student.admNo} and course is ${student.course}")
        } catch (e: Exception) {

            println(" Error => ${e.message}")

            println("Error deserializing message: $userJson Error => ${e.message}")
        }
    }*/

    fun enrollStudent(courseEnrollmentDto: CourseEnrollmentDto){
        val enrollmentExists = courseEnrollmentRepository
            .existsByCourseIdAndStudentId(
            courseEnrollmentDto.courseId,
            courseEnrollmentDto.studentId,
        )

        println(enrollmentExists)

        if (!enrollmentExists){
            val courseEnrollment = CourseEnrollment(
                id = null,
                studentId = courseEnrollmentDto.studentId,
                courseId = courseEnrollmentDto.courseId,
            )
            courseEnrollmentRepository.save(courseEnrollment)
        }
    }

    fun getStudentCourse(studentId: String): MutableList<CourseDepartmentDto> {
        val response = mutableListOf<CourseDepartmentDto>()
        courseRepository.getStudentCourse(studentId).forEach { course->
            if (course is Array<*>){
                val courseName = course[0].toString()
                val departmentName = course[1].toString()

                val courseDepartmentDto = CourseDepartmentDto(courseName,departmentName)
                response.add(courseDepartmentDto)
            }
        }
        return response
    }
}