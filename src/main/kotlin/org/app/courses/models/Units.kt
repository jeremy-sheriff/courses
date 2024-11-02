package org.app.courses.models

import jakarta.persistence.*
import java.util.*


@Table(name = "units")
@Entity
open class Units(
    @Id
    @GeneratedValue(generator = "UUID")
    open var uuid: UUID,

    @Column(name = "name", nullable = false)
    open var name: String,

    @Column(name = "courseId", nullable = false)
    open var courseId: Long,
){
    constructor() : this(UUID.randomUUID(),"",1) {

    }
}