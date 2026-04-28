package com.autistasgourmet.pethub.data.remote.dto

data class AdopterProfileDto(
    val userId: String = "",
    val name: String = "",
    val lastName: String = "",
    val age: Int = 0,
    val occupation: String = "",
    val postalCode: String = "",
    val city: String = "",
    val housingType: String = "",
    val hasPatio: Boolean = false,
    val spaceRoutineDetails: String = "",
    val petExperience: String = "",
    val hasDogs: Boolean = false,
    val hasCats: Boolean = false,
    val hasKids: Boolean = false,
    val vetVisits: Boolean = false,
    val vaccination: Boolean = false,
    val deworming: Boolean = false,
    val properHygiene: Boolean = false,
    val cleanWater: Boolean = false,
    val kibbleFeeding: Boolean = false
)
