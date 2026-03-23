package com.autistasgourmet.pethub.domain.repository

import com.autistasgourmet.pethub.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    /**
     * Expone el estado del usuario actual, null si no hay nadie logueado
     */
    val currentUser: Flow<User?>

    /**
     * Intenta iniciar sesion con correo y contraseña
     */
    suspend fun login(email: String, pass: String): Result<User>

    /**
     * Registra un nuevo usuario en Firebase y regresa sus datos
     */
    suspend fun register(email: String, pass: String, name: String): Result<User>

    /**
     * Cierra la sesion activa del usuario
     */
    suspend fun logout()
}
