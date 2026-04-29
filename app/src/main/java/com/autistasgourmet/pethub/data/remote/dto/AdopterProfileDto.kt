package com.autistasgourmet.pethub.data.remote.dto

import com.google.firebase.firestore.PropertyName

data class AdopterProfileDto(
    val userId: String = "",
    val name: String = "",
    val lastName: String = "",
    val age: Int = 0,
    val occupation: String = "",
    val postalCode: String = "",
    val city: String = "",
    val housingType: String = "",
    @get:PropertyName("hasPatio")
    @set:PropertyName("hasPatio")
    var hasPatio: Boolean = false,
    val spaceRoutineDetails: String = "",
    val petExperience: String = "",
    @get:PropertyName("hasDogs")
    @set:PropertyName("hasDogs")
    var hasDogs: Boolean = false,
    @get:PropertyName("hasCats")
    @set:PropertyName("hasCats")
    var hasCats: Boolean = false,
    @get:PropertyName("hasKids")
    @set:PropertyName("hasKids")
    var hasKids: Boolean = false,
    @get:PropertyName("vetVisits")
    @set:PropertyName("vetVisits")
    var vetVisits: Boolean = false,
    @get:PropertyName("vaccination")
    @set:PropertyName("vaccination")
    var vaccination: Boolean = false,
    @get:PropertyName("deworming")
    @set:PropertyName("deworming")
    var deworming: Boolean = false,
    @get:PropertyName("properHygiene")
    @set:PropertyName("properHygiene")
    var properHygiene: Boolean = false,
    @get:PropertyName("cleanWater")
    @set:PropertyName("cleanWater")
    var cleanWater: Boolean = false,
    @get:PropertyName("kibbleFeeding")
    @set:PropertyName("kibbleFeeding")
    var kibbleFeeding: Boolean = false
)
