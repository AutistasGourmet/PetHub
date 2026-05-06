package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.repository.PetRepository

sealed class SavePetError : Exception() {
    object EmptyName : SavePetError()
    object EmptyDescription : SavePetError()
    object NoPhotos : SavePetError()
    object EmptyMunicipality : SavePetError()
    data class InvalidFields(val errors: List<SavePetError>) : SavePetError()
}

class SavePetUseCase(
    private val repository: PetRepository
) {

    suspend operator fun invoke(pet: Pet): Result<Unit> {
        val errors = mutableListOf<SavePetError>()

        // validaciones
        if (pet.name.isBlank()) errors.add(SavePetError.EmptyName)
        if (pet.description.isBlank()) errors.add(SavePetError.EmptyDescription)
        if (pet.photos.isEmpty()) errors.add(SavePetError.NoPhotos)

        if (errors.isNotEmpty()) {
            return Result.failure(SavePetError.InvalidFields(errors))
        }

        return repository.savePet(pet)
    }
}
