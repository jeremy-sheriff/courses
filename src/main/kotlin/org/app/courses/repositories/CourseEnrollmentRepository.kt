package org.app.courses.repositories

import org.app.courses.models.CourseEnrollment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CourseEnrollmentRepository:JpaRepository<CourseEnrollment,Long>{

    fun existsByCourseIdAndStudentId(courseId:String,studentId:UUID):Boolean
}