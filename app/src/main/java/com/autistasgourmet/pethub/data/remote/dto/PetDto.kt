package com.autistasgourmet.pethub.data.remote.dto

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
    val isVaccinated: Boolean = false,
    val isSterilized: Boolean = false,
    val isDewormed: Boolean = false,
    val specialConditions: String = "",
    val postalCode: String = "",
    val city: String = ""
)
