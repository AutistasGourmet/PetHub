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
    val traits: List<PetTrait>,
    val photos: List<String>,
    val description: String,
    val isVaccinated: Boolean,
    val isSterilized: Boolean,
    val isDewormed: Boolean,
    val specialConditions: String,
    val postalCode: String,
    val city: String
)
