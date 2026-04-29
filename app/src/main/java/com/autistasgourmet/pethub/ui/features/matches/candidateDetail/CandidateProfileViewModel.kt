package com.autistasgourmet.pethub.ui.features.matches.candidateDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.model.AdopterExperience
import com.autistasgourmet.pethub.domain.usecase.GetAdopterProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val getAdopterProfileUseCase: GetAdopterProfileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CandidateProfileUiState())
    val uiState: StateFlow<CandidateProfileUiState> = _uiState.asStateFlow()

    private val candidateId: String = checkNotNull(savedStateHandle["userId"])
    private val petName: String = checkNotNull(savedStateHandle["petName"])

    init {
        loadCandidateProfile()
    }

    private fun loadCandidateProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }

            try {
                val profile = getAdopterProfileUseCase(candidateId)
                
                if (profile == null) {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = "El perfil del adoptante no está disponible o ha sido eliminado."
                        )
                    }
                    return@launch
                }

                val data = CandidateProfileData(
                    userId = profile.userId,
                    userName = "${profile.name} ${profile.lastName}",
                    occupation = profile.occupation,
                    age = "${profile.age} años",
                    location = "${profile.city}, Zac.",
                    interestedInPetName = petName,
                    housingType = profile.housingType.displayName,
                    patioDetails = if (profile.hasPatio) "Tiene patio/jardín" else "Sin patio / Terraza / Jardín",
                    kidsDetails = "Tengo Hijos en Casa: ${if (profile.hasKids) "Sí" else "No"}",
                    experienceDetails = when (profile.petExperience) {
                        AdopterExperience.PRIMERA_VEZ -> "Es mi primera vez teniendo mascotas"
                        AdopterExperience.PASADO -> "He tenido mascotas en el pasado"
                        AdopterExperience.ACTUALMENTE -> "Tengo mascotas actualmente"
                        AdopterExperience.RESCATISTA -> "Tengo mucha experiencia / Soy rescatista"
                    },
                    otherPetsDogs = "Perros: ${if (profile.hasDogs) "Sí" else "No"}",
                    otherPetsCats = "Gatos: ${if (profile.hasCats) "Sí" else "No"}",
                    routineDetails = profile.spaceRoutineDetails,
                    careDetails = mutableListOf<String>().apply {
                        if (profile.vetVisits) add("Visitas al veterinario: Sí")
                        if (profile.vaccination) add("Vacunación: Sí")
                        if (profile.deworming) add("Desparasitación: Sí")
                        if (profile.properHygiene) add("Higiene adecuada: Sí")
                    }
                )

                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        profile = data
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message ?: "Error al cargar el perfil."
                    )
                }
            }
        }
    }
}
