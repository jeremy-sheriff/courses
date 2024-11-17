package org.app.courses.services

import org.app.courses.models.Units
import org.app.courses.repositories.UnitsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UnitsService(
    private val unitsRepository: UnitsRepository
) {

    fun getUnits(page:Int,size:Int): Page<Units> {
        val pageable: Pageable = PageRequest.of(page, size)
        return unitsRepository.findAll(pageable)
    }
}