package com.autistasgourmet.pethub.domain.repository

import com.autistasgourmet.pethub.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    /**
     * Obtiene la lista de todas las mascotas registradas para adopcion
     */
    fun getPets(): Flow<List<Pet>>

    /**
     * Obtiene solo las mascotas que fueron publicadas por un usuario
     */
    fun getPetsByOwner(ownerEmail: String): Flow<List<Pet>>

    /**
     * Busca los detalles de una mascota con su id
     */
    suspend fun getPetById(id: String): Pet?

    /**
     * Crea una nueva o actualiza la mascota en la base de datos
     */
    suspend fun savePet(pet: Pet): Result<Unit>

    /**
     * Elimina una mascota de la base de datos
     */
    suspend fun deletePet(id: String): Result<Unit>
}
