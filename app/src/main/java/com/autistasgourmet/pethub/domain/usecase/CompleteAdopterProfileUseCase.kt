package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository

sealed class AdopterProfileError : Exception() {
    object EmptyName : AdopterProfileError()
    object EmptyLastName : AdopterProfileError()
    object InvalidAge : AdopterProfileError()
    object EmptyOccupation : AdopterProfileError()
    object EmptySpaceDetails : AdopterProfileError()
    data class InvalidFields(val errors: List<AdopterProfileError>) : AdopterProfileError()
}

class CompleteAdopterProfileUseCase(
    private val repository: AdopterProfileRepository
) {

    suspend operator fun invoke(profile: AdopterProfile): Result<Unit> {
        val errors = mutableListOf<AdopterProfileError>()

        if (profile.name.isBlank()) errors.add(AdopterProfileError.EmptyName)
        if (profile.lastName.isBlank()) errors.add(AdopterProfileError.EmptyLastName)
        if (profile.age <= 0) errors.add(AdopterProfileError.InvalidAge)
        if (profile.occupation.isBlank()) errors.add(AdopterProfileError.EmptyOccupation)
        if (profile.spaceRoutineDetails.isBlank()) errors.add(AdopterProfileError.EmptySpaceDetails)

        if (errors.isNotEmpty()) {
            return Result.failure(AdopterProfileError.InvalidFields(errors))
        }

        return repository.saveProfile(profile)
    }
}
