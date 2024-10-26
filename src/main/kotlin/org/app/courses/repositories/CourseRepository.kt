package org.app.courses.repositories

import org.app.courses.models.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface CourseRepository : JpaRepository<Course, Long> {


    @Query(
        """
        SELECT
            courses.name,
            departments.name AS department
        FROM
            courses
        JOIN 
            course_enrollment on CAST(course_enrollment.course_id AS BIGINT)= CAST(courses.id AS BIGINT)
            JOIN public.departments  on departments.id = courses.department_id
        WHERE 
            student_id = :studentId 
    """, nativeQuery = true
    )
    fun getStudentCourse(studentId: String): MutableList<Any>
}