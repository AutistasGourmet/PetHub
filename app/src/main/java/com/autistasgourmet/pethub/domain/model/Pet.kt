package com.autistasgourmet.pethub.domain.model

data class Pet(
    val id: String = "",
    val ownerEmail: String,
    val name: String,
    val species: PetSpecies,
    val ageRange: PetAgeRange,
    val size: PetSize,
    val gender: PetGender,
    val energyLevel: EnergyLevel,
    val sociabilityKids: SociabilityLevel,
    val sociabilityDogs: SociabilityLevel,
    val sociabilityCats: SociabilityLevel,
    val affectionTraits: List<String>,
    val energyTraits: List<String>,
    val intelligenceTraits: List<String>,
    val instinctTraits: List<String>,
    val photos: List<String>,
    val description: String,
    val isVaccinated: Boolean,
    val isSterilized: Boolean,
    val isDewormed: Boolean,
    val specialConditions: String,
    val postalCode: String,
    val city: String
)