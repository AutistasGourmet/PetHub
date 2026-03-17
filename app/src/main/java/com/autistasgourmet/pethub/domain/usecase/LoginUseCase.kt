package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.User
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import android.util.Patterns

sealed class LoginError : Exception() {
    object EmptyEmail : LoginError()
    object InvalidEmail : LoginError()
    object EmptyPassword : LoginError()
    data class InvalidFields(val errors: List<LoginError>) : LoginError()
}

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, pass: String): Result<User> {
        val errors = mutableListOf<LoginError>()

        if (email.isBlank()) errors.add(LoginError.EmptyEmail)
        else if (!Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) errors.add(LoginError.InvalidEmail)

        if (pass.isBlank()) errors.add(LoginError.EmptyPassword)

        return if (errors.isNotEmpty()) {
            Result.failure(LoginError.InvalidFields(errors))
        } else {
            repository.login(email, pass)
        }
    }
}
