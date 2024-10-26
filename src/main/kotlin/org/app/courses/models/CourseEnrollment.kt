package org.app.courses.models

import jakarta.persistence.*

@Table(name = "course_enrollment")
@Entity
open class CourseEnrollment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:Long?,

    @Column(name = "studentId", nullable = false)
    open var studentId: String,

    @Column(name = "courseId", nullable = false)
    open var courseId: String,
){
    constructor() : this(1,"","") {

    }

}