package org.app.courses.services

import org.app.courses.dto.CourseDepartmentDto
import org.app.courses.dto.CourseDto
import org.app.courses.dto.CourseEnrollmentDto
import org.app.courses.models.Course
import org.app.courses.models.CourseEnrollment
import org.app.courses.repositories.CourseEnrollmentRepository
import org.app.courses.repositories.CourseRepository
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.app.courses.dto.UserDto
import org.slf4j.LoggerFactory
import java.util.UUID

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val departmentService: DepartmentService,
    private val courseEnrollmentRepository: CourseEnrollmentRepository,
){

    private val logger = LoggerFactory.getLogger(CourseService::class.java)


    fun getAllCourses(page: Int, size: Int): Page<Course> {
        val pageable = PageRequest.of(page, size)
        return courseRepository.findAll(pageable)
    }

    fun getUnpaginatedCourses(): MutableList<Course> {
        return courseRepository.findAll()
    }

    @SqsListener("\${aws.sqs.queueUrl}")
    fun receiveMessage(message: String) {
        println("Received message: $message")

        val objectMapper = jacksonObjectMapper()
        try {
            // Parse the JSON string to the StudentMessage data class
            val studentMessage: UserDto = objectMapper.readValue(message)

            // Extract individual variables
            val id = studentMessage.id
            val name = studentMessage.name
            val admNo = studentMessage.admNo
            val course = studentMessage.course

            val courseEnrollmentDto = CourseEnrollmentDto(
                id = null,  // Assuming id is auto-generated for new enrollments
                studentId = id,  // Use admission number as student ID
                courseId = course   // Use course ID as provided
            )

            // Call enrollStudent with the CourseEnrollmentDto
            enrollStudent(courseEnrollmentDto)

          logger.info("Student with id: $id, name: $name, admNo: $admNo, course: $course")
        } catch (e: Exception) {
            println("Error parsing message: ${e.message}")
        }
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

    fun getStudentCourse(studentId: UUID): MutableList<CourseDepartmentDto> {
        val response = mutableListOf<CourseDepartmentDto>()
        courseRepository.getStudentCourse(studentId).firstOrNull()?.let { course ->
            logger.info("Course is $course")
            if (course is Array<*> && course.size >= 2) {
                val courseName = course[0].toString()
                val departmentName = course[1].toString()
                val courseDepartmentDto = CourseDepartmentDto(courseName, departmentName)
                response.add(courseDepartmentDto)
            }
        }
        return response
    }



    fun getCourseTakenByStudent(studentId: String){

    }
}