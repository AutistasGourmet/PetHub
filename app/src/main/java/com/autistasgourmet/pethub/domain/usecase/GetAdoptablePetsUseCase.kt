package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.repository.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAdoptablePetsUseCase(
    private val repository: PetRepository
) {
    operator fun invoke(): Flow<List<Pet>> {
        return repository.getPets().map { pets ->
            pets.shuffled()
        }
    }
}
