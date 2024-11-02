package org.app.courses.models

import jakarta.persistence.*
import java.util.UUID

@Table(name = "course_enrollment")
@Entity
open class CourseEnrollment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:Long?,

    @Column(name = "studentId", nullable = false)
    open var studentId: UUID,

    @Column(name = "courseId", nullable = false)
    open var courseId: String,
){
    constructor() : this(1,UUID.randomUUID(),"") {

    }

}