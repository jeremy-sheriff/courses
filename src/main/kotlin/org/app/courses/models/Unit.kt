package org.app.courses.models

import jakarta.persistence.*
import org.hibernate.validator.constraints.UUID

open class Unit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:UUID?,

    @Column(name = "name", nullable = false)
    open var name: String,

    @Column(name = "courseId", nullable = false)
    open var courseId: Long,
){
    constructor() : this(null,"",1) {

    }
}