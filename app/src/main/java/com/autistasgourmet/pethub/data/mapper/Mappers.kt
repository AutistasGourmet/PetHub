package com.autistasgourmet.pethub.data.mapper

import com.autistasgourmet.pethub.data.remote.dto.AdopterProfileDto
import com.autistasgourmet.pethub.data.remote.dto.PetDto
import com.autistasgourmet.pethub.data.remote.dto.UserDto
import com.autistasgourmet.pethub.domain.model.AdopterExperience
import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.domain.model.EnergyLevel
import com.autistasgourmet.pethub.domain.model.HousingType
import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.model.PetAgeRange
import com.autistasgourmet.pethub.domain.model.PetGender
import com.autistasgourmet.pethub.domain.model.PetSize
import com.autistasgourmet.pethub.domain.model.PetSpecies
import com.autistasgourmet.pethub.domain.model.SociabilityLevel
import com.autistasgourmet.pethub.domain.model.User

// User ------------------------------------------------------------------

fun UserDto.toDomain(): User = User(
    uid = uid,
    email = email,
    name = name
)

fun User.toDto(): UserDto = UserDto(
    uid = uid,
    email = email,
    name = name
)


// Pet ------------------------------------------------------------------

fun PetDto.toDomain(): Pet = Pet(
    id = id,
    ownerEmail = ownerEmail,
    name = name,
    species = enumValueOf(species),
    ageRange = enumValueOf(ageRange),
    size = enumValueOf(size),
    gender = enumValueOf(gender),
    energyLevel = enumValueOf(energyLevel),
    sociabilityKids = enumValueOf(sociabilityKids),
    sociabilityDogs = enumValueOf(sociabilityDogs),
    sociabilityCats = enumValueOf(sociabilityCats),
    affectionTraits = affectionTraits,
    energyTraits = energyTraits,
    intelligenceTraits = intelligenceTraits,
    instinctTraits = instinctTraits,
    photos = photos,
    description = description,
    isVaccinated = isVaccinated,
    isSterilized = isSterilized,
    isDewormed = isDewormed,
    specialConditions = specialConditions,
    postalCode = postalCode,
    state = state,
    city = city
)

fun Pet.toDto(): PetDto = PetDto(
    id = id,
    ownerEmail = ownerEmail,
    name = name,
    species = species.name,
    ageRange = ageRange.name,
    size = size.name,
    gender = gender.name,
    energyLevel = energyLevel.name,
    sociabilityKids = sociabilityKids.name,
    sociabilityDogs = sociabilityDogs.name,
    sociabilityCats = sociabilityCats.name,
    affectionTraits = affectionTraits,
    energyTraits = energyTraits,
    intelligenceTraits = intelligenceTraits,
    instinctTraits = instinctTraits,
    photos = photos,
    description = description,
    isVaccinated = isVaccinated,
    isSterilized = isSterilized,
    isDewormed = isDewormed,
    specialConditions = specialConditions,
    postalCode = postalCode,
    state = state,
    city = city
)


// AdopterProfile ------------------------------------------------------------------

fun AdopterProfileDto.toDomain(): AdopterProfile = AdopterProfile(
    userId = userId,
    name = name,
    lastName = lastName,
    age = age,
    occupation = occupation,
    postalCode = postalCode,
    state = state,
    city = city,
    housingType = enumValueOf(housingType),
    hasPatio = hasPatio,
    spaceRoutineDetails = spaceRoutineDetails,
    petExperience = enumValueOf(petExperience),
    hasDogs = hasDogs,
    hasCats = hasCats,
    hasKids = hasKids,
    vetVisits = vetVisits,
    vaccination = vaccination,
    deworming = deworming,
    properHygiene = properHygiene,
    cleanWater = cleanWater,
    kibbleFeeding = kibbleFeeding
)

fun AdopterProfile.toDto(): AdopterProfileDto = AdopterProfileDto(
    userId = userId,
    name = name,
    lastName = lastName,
    age = age,
    occupation = occupation,
    postalCode = postalCode,
    state = state,
    city = city,
    housingType = housingType.name,
    hasPatio = hasPatio,
    spaceRoutineDetails = spaceRoutineDetails,
    petExperience = petExperience.name,
    hasDogs = hasDogs,
    hasCats = hasCats,
    hasKids = hasKids,
    vetVisits = vetVisits,
    vaccination = vaccination,
    deworming = deworming,
    properHygiene = properHygiene,
    cleanWater = cleanWater,
    kibbleFeeding = kibbleFeeding
)