package com.autistasgourmet.pethub.domain.repository

import com.autistasgourmet.pethub.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun getPets(): Flow<List<Pet>>
    fun getPetsByOwner(ownerEmail: String): Flow<List<Pet>>
    suspend fun getPetById(id: String): Pet?
    suspend fun savePet(pet: Pet): Result<Unit>
    suspend fun deletePet(id: String): Result<Unit>

    // Fotos en Base64
    suspend fun savePetPhoto(base64: String): Result<String>
    suspend fun getPetPhoto(photoId: String): Result<String>
}
