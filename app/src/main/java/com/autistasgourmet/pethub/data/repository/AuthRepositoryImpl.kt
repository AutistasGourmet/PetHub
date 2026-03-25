package com.autistasgourmet.pethub.data.repository

import com.autistasgourmet.pethub.domain.model.User
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            val user = firebaseUser?.let {
                User(
                    uid = it.uid,
                    email = it.email ?: "",
                    name = it.displayName ?: ""
                )
            }
            trySend(user)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun login(email: String, pass: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, pass).await()
            val firebaseUser = result.user ?: throw Exception("Usuario no encontrado")
            Result.success(
                User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = firebaseUser.displayName ?: ""
                )
            )
        } catch (e: Exception) {
            Result.failure(Exception(mapFirebaseError(e)))
        }
    }

    override suspend fun register(email: String, pass: String, name: String): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
            val firebaseUser = result.user ?: throw Exception("No se pudo crear el usuario")
            
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            firebaseUser.updateProfile(profileUpdates).await()

            Result.success(
                User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    name = name
                )
            )
        } catch (e: Exception) {
            Result.failure(Exception(mapFirebaseError(e)))
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    private fun mapFirebaseError(e: Exception): String {
        return when (e) {
            is FirebaseNetworkException -> "Sin conexión a internet. Revisa tu red."
            is FirebaseAuthInvalidUserException -> "Este usuario no existe o ha sido deshabilitado."
            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrectos."
            is FirebaseAuthUserCollisionException -> "Este correo electrónico ya está registrado."
            is FirebaseAuthException -> {
                when (e.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "El formato del correo electrónico no es válido."
                    "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta."
                    "ERROR_USER_NOT_FOUND" -> "No hay ningún usuario registrado con este correo."
                    "ERROR_TOO_MANY_REQUESTS" -> "Demasiados intentos. Inténtalo más tarde."
                    else -> "Error de autenticación: ${e.message}"
                }
            }

            else -> "Ocurrió un error inesperado. Inténtalo de nuevo."
        }
    }
}
