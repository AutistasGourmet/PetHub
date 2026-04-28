package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository
import com.autistasgourmet.pethub.domain.repository.PostalCodeRepository

sealed class AdopterProfileError : Exception() {
    object EmptyName : AdopterProfileError()
    object EmptyLastName : AdopterProfileError()
    object InvalidAge : AdopterProfileError()
    object EmptyOccupation : AdopterProfileError()
    object InvalidPostalCode : AdopterProfileError()
    object EmptySpaceDetails : AdopterProfileError()
    data class InvalidFields(val errors: List<AdopterProfileError>) : AdopterProfileError()
}

class CompleteAdopterProfileUseCase(
    private val repository: AdopterProfileRepository,
    private val postalCodeRepository: PostalCodeRepository
) {

    suspend operator fun invoke(profile: AdopterProfile): Result<Unit> {
        val errors = mutableListOf<AdopterProfileError>()

        if (profile.name.isBlank()) errors.add(AdopterProfileError.EmptyName)
        if (profile.lastName.isBlank()) errors.add(AdopterProfileError.EmptyLastName)
        if (profile.age <= 0) errors.add(AdopterProfileError.InvalidAge)
        if (profile.occupation.isBlank()) errors.add(AdopterProfileError.EmptyOccupation)
        if (profile.spaceRoutineDetails.isBlank()) errors.add(AdopterProfileError.EmptySpaceDetails)

        val isPostalCodeFormatValid =
            profile.postalCode.length == 5 && profile.postalCode.all { it.isDigit() }

        val municipality = if (isPostalCodeFormatValid) {
            postalCodeRepository.getMunicipalityForPostalCode(profile.postalCode)
        } else null

        if (municipality == null) {
            errors.add(AdopterProfileError.InvalidPostalCode)
        }

        if (errors.isNotEmpty()) {
            return Result.failure(AdopterProfileError.InvalidFields(errors))
        }

        val enrichedProfile = profile.copy(
            city = municipality?.displayName ?: ""
        )

        return repository.saveProfile(enrichedProfile)
    }
}
