package org.app.courses.controllers

import org.app.courses.models.Units
import org.app.courses.services.UnitsService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/units")
@CrossOrigin(
    origins = ["http://localhost:4200",
    "https://api.muhohodev.com",
    "https://muhohodev.com"])
@PreAuthorize("hasAnyAuthority('units_role')")
class UnitsController(
    private val unitsService: UnitsService,
) {

    private val logger = LoggerFactory.getLogger(UnitsController::class.java)


    @GetMapping("")
    fun getUnits(@RequestParam(defaultValue = "0") page: Int,
                 @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<MutableList<Units>> {
        val users = unitsService.getUnits(page, size).content.toMutableList()
        return ResponseEntity.ok(users)
    }
}