package com.autistasgourmet.pethub.domain.model

data class AdopterProfile(
    val userId: String,
    val name: String,
    val lastName: String,
    val age: Int,
    val occupation: String,
    val postalCode: String,
    val state: String,
    val city: String,
    val housingType: HousingType,
    val hasPatio: Boolean,
    val spaceRoutineDetails: String,
    val petExperience: AdopterExperience,
    val hasDogs: Boolean,
    val hasCats: Boolean,
    val hasKids: Boolean,
    val vetVisits: Boolean,
    val vaccination: Boolean,
    val deworming: Boolean,
    val properHygiene: Boolean,
    val cleanWater: Boolean,
    val kibbleFeeding: Boolean
)
