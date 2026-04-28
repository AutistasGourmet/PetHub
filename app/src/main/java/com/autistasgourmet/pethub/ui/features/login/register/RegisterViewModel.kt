package com.autistasgourmet.pethub.ui.features.login.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.usecase.RegisterError
import com.autistasgourmet.pethub.domain.usecase.RegisterUseCase
import com.autistasgourmet.pethub.ui.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.FirebaseNetworkException

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var nameError by mutableStateOf<String?>(null)
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    private val _registerSuccess = MutableSharedFlow<Boolean>()
    val registerSuccess = _registerSuccess.asSharedFlow()

    fun onNameChange(newValue: String) {
        name = newValue
        nameError = null
    }

    fun onEmailChange(newValue: String) {
        email = newValue
        emailError = null
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        passwordError = null
    }

    fun register() {
        viewModelScope.launch {
            isLoading = true
            nameError = null
            emailError = null
            passwordError = null
            
            val result = registerUseCase(email, password, name)
            result.onSuccess {
                _registerSuccess.emit(true)
            }.onFailure { e ->
                when (e) {
                    is RegisterError.InvalidFields -> {
                        e.errors.forEach { error ->
                            when (error) {
                                is RegisterError.EmptyName -> nameError = "Campo obligatorio"
                                is RegisterError.EmptyEmail -> emailError = "Campo obligatorio"
                                is RegisterError.InvalidEmail -> emailError = "Correo electrónico no válido"
                                is RegisterError.EmptyPassword -> passwordError = "Campo obligatorio"
                                is RegisterError.PasswordTooShort -> passwordError = "Mínimo 6 caracteres"
                                else -> {}
                            }
                        }
                    }
                    is FirebaseNetworkException -> SnackbarManager.showMessage("Error de conexión. Revisa tu internet.")
                    else -> SnackbarManager.showMessage(e.message ?: "Error al registrarse")
                }
            }
            isLoading = false
        }
    }
}
