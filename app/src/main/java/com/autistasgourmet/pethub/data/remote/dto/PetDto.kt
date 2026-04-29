package com.autistasgourmet.pethub.data.remote.dto

import com.google.firebase.firestore.PropertyName

data class PetDto(
    val id: String = "",
    val ownerEmail: String = "",
    val name: String = "",
    val species: String = "",
    val ageRange: String = "",
    val size: String = "",
    val gender: String = "",
    val energyLevel: String = "",
    val sociabilityKids: String = "",
    val sociabilityDogs: String = "",
    val sociabilityCats: String = "",
    val traits: List<String> = emptyList(),
    val photos: List<String> = emptyList(),
    val description: String = "",
    @get:PropertyName("vaccinated")
    @set:PropertyName("vaccinated")
    var isVaccinated: Boolean = false,
    @get:PropertyName("sterilized")
    @set:PropertyName("sterilized")
    var isSterilized: Boolean = false,
    @get:PropertyName("dewormed")
    @set:PropertyName("dewormed")
    var isDewormed: Boolean = false,
    val specialConditions: String = "",
    val postalCode: String = "",
    val city: String = ""
)
