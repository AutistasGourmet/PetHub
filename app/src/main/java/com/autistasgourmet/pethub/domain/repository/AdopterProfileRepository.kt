package com.autistasgourmet.pethub.domain.repository

import com.autistasgourmet.pethub.domain.model.AdopterProfile

interface AdopterProfileRepository {
    /**
     * Obtiene la información del perfil de adoptante de un usuario
     */
    suspend fun getProfile(userId: String): AdopterProfile?

    /**
     * Guarda o actualiza los datos del perfil del adoptante
     */
    suspend fun saveProfile(profile: AdopterProfile): Result<Unit>

    /**
     * Elimina el perfil de adoptante de la base de datos
     */
    suspend fun deleteProfile(userId: String): Result<Unit>
}
