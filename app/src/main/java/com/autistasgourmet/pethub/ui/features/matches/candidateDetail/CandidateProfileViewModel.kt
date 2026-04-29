package com.autistasgourmet.pethub.ui.features.matches.candidateDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CandidateProfileData(
    val userId: String,
    val userName: String,
    val occupation: String,
    val age: String,
    val location: String,
    val interestedInPetName: String,
    val housingType: String,
    val patioDetails: String,
    val kidsDetails: String,
    val experienceDetails: String,
    val otherPetsDogs: String,
    val otherPetsCats: String,
    val routineDetails: String,
    val careDetails: List<String>
)

data class CandidateProfileUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false, // Para manejar Excepción E1
    val errorMessage: String? = null,
    val profile: CandidateProfileData? = null
)

@HiltViewModel
class CandidateProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CandidateProfileUiState())
    val uiState: StateFlow<CandidateProfileUiState> = _uiState.asStateFlow()

    private val candidateId: String = checkNotNull(savedStateHandle["userId"])

    init {
        loadCandidateProfile()
    }

    private fun loadCandidateProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isError = false)
            
            // Simulamos un retraso de red
            delay(800)

            // Simulación de Excepción E1: Retiro de interés repentino
            if (candidateId == "user_error") {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "El adoptante ha retirado su solicitud o eliminado su cuenta. El perfil ya no se encuentra disponible."
                )
                return@launch
            }

            // Datos mockeados de éxito
            val mockProfile = CandidateProfileData(
                userId = candidateId,
                userName = "Ángel Segovia",
                occupation = "Licenciado en Fisioterapia",
                age = "22 años",
                location = "Guadalupe, Zac.",
                interestedInPetName = "Toby",
                housingType = "Apartamento",
                patioDetails = "Sin patio / Terraza / Jardín",
                kidsDetails = "Tengo Hijos en Casa: No",
                experienceDetails = "Tengo Mascotas Actualmente",
                otherPetsDogs = "Perros: No",
                otherPetsCats = "Gatos: No",
                routineDetails = "Trabajo de lunes a viernes solamente por la mañana y desde casa, por lo que tengo tiempo para cuidar de una mascota.",
                careDetails = listOf(
                    "Visitas al veterinario: Si",
                    "Vacunación: Si",
                    "Desparasitación: Si"
                )
            )

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                profile = mockProfile
            )
        }
    }
}
