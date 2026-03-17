package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.User
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import android.util.Patterns

sealed class RegisterError : Exception() {
    object EmptyName : RegisterError()
    object EmptyEmail : RegisterError()
    object InvalidEmail : RegisterError()
    object EmptyPassword : RegisterError()
    object PasswordTooShort : RegisterError()
    data class InvalidFields(val errors: List<RegisterError>) : RegisterError()
}

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, pass: String, name: String): Result<User> {
        val errors = mutableListOf<RegisterError>()

        if (name.isBlank()) errors.add(RegisterError.EmptyName)

        if (email.isBlank()) errors.add(RegisterError.EmptyEmail)
        else if (!Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) errors.add(RegisterError.InvalidEmail)

        if (pass.isBlank()) errors.add(RegisterError.EmptyPassword)
        else if (pass.length < 6) errors.add(RegisterError.PasswordTooShort)

        return if (errors.isNotEmpty()) {
            Result.failure(RegisterError.InvalidFields(errors))
        } else {
            repository.register(email, pass, name)
        }
    }
}
