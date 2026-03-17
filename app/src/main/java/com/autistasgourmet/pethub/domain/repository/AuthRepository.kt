package com.autistasgourmet.pethub.domain.repository

import com.autistasgourmet.pethub.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, pass: String): Result<User>
    suspend fun register(email: String, pass: String, name: String): Result<User>
    suspend fun logout()
}
