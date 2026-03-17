package com.autistasgourmet.pethub.domain.repository

import com.autistasgourmet.pethub.domain.model.Pet
import kotlinx.coroutines.flow.Flow

// ejemplo
interface PetRepository {
    fun getPets(): Flow<List<Pet>>
    suspend fun getPetById(id: String): Pet?
}
