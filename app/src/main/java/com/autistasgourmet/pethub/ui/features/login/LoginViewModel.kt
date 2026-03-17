package com.autistasgourmet.pethub.ui.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.usecase.LoginError
import com.autistasgourmet.pethub.domain.usecase.LoginUseCase
import com.autistasgourmet.pethub.ui.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.FirebaseNetworkException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    private val _loginSuccess = MutableSharedFlow<Boolean>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    fun onEmailChange(newValue: String) {
        email = newValue
        emailError = null
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        passwordError = null
    }

    fun login() {
        viewModelScope.launch {
            isLoading = true
            emailError = null
            passwordError = null
            
            val result = loginUseCase(email, password)
            result.onSuccess {
                _loginSuccess.emit(true)
            }.onFailure { e ->
                when (e) {
                    is LoginError.InvalidFields -> {
                        e.errors.forEach { error ->
                            when (error) {
                                is LoginError.EmptyEmail -> emailError = "Campo obligatorio"
                                is LoginError.InvalidEmail -> emailError = "Formato de correo inválido"
                                is LoginError.EmptyPassword -> passwordError = "Campo obligatorio"
                                else -> {}
                            }
                        }
                    }
                    is FirebaseNetworkException -> SnackbarManager.showMessage("Error de conexión. Revisa tu internet.")
                    else -> SnackbarManager.showMessage(e.message ?: "Error al iniciar sesión")
                }
            }
            isLoading = false
        }
    }
}
