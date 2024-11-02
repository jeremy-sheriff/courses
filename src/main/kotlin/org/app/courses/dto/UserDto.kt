package org.app.courses.dto

import java.util.UUID

data class UserDto(
    val id:UUID,
    val name:String,
    val admNo:String,
    val course:String,
)