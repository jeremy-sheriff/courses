package org.app.courses.repositories

import org.app.courses.models.Course
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface CourseRepository : JpaRepository<Course, Long> {


    @Query(
        """
    SELECT
        c.name AS courseName,
        d.name AS departmentName
    FROM
        courses c
    JOIN 
        course_enrollment ce ON CAST(ce.course_id AS BIGINT) = c.id
    JOIN 
        departments d ON d.id = c.department_id
    WHERE 
        ce.student_id = :studentId
    """, nativeQuery = true
    )
    fun getStudentCourse(studentId: UUID): MutableList<Any>



    override fun findAll(pageable: Pageable): Page<Course>
}