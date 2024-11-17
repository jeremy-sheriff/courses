package org.app.courses.repositories

import org.app.courses.models.Units
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID


interface UnitsRepository: JpaRepository<Units, UUID> {


    // Pageable method for getting students with pagination
    override fun findAll(pageable: Pageable): Page<Units>
}