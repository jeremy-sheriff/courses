package org.app.courses.dto

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotEmpty
import java.util.UUID

data class CourseEnrollmentDto(

     var id:Long?,

    @NotEmpty(message = "Student cannot be empty")
      var studentId: UUID,

     @NotEmpty(message = "Course cannot be empty")
     var courseId: String,
)