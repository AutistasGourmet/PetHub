package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.repository.PetRepository
import com.autistasgourmet.pethub.domain.repository.PostalCodeRepository

sealed class SavePetError : Exception() {
    object EmptyName : SavePetError()
    object EmptyDescription : SavePetError()
    object NoPhotos : SavePetError()
    object InvalidPostalCode : SavePetError()
    data class InvalidFields(val errors: List<SavePetError>) : SavePetError()
}

class SavePetUseCase(
    private val repository: PetRepository,
    private val postalCodeRepository: PostalCodeRepository
) {

    suspend operator fun invoke(pet: Pet): Result<Unit> {
        val errors = mutableListOf<SavePetError>()

        // validaciones
        if (pet.name.isBlank()) errors.add(SavePetError.EmptyName)
        if (pet.description.isBlank()) errors.add(SavePetError.EmptyDescription)
        if (pet.photos.isEmpty()) errors.add(SavePetError.NoPhotos)

        // validaciones del codigo postal
        val isPostalCodeFormatValid =
            pet.postalCode.length == 5 && pet.postalCode.all { it.isDigit() }

        val municipality = if (isPostalCodeFormatValid) {
            postalCodeRepository.getMunicipalityForPostalCode(pet.postalCode)
        } else null

        if (municipality == null) {
            errors.add(SavePetError.InvalidPostalCode)
        }

        if (errors.isNotEmpty()) {
            return Result.failure(SavePetError.InvalidFields(errors))
        }

        val petWithCity = pet.copy(
            city = municipality?.displayName ?: ""
        )

        return  repository.savePet(petWithCity)
    }
}