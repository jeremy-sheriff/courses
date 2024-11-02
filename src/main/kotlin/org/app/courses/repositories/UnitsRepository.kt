package org.app.courses.repositories

import org.app.courses.models.Units
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID


interface UnitRepository: JpaRepository<Units, UUID> {
}