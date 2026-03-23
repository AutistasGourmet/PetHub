package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.repository.PetRepository

class GetPetDetailUseCase(
    private val repository: PetRepository
) {
    suspend operator fun invoke(id: String): Pet? {
        return repository.getPetById(id)
    }
}
