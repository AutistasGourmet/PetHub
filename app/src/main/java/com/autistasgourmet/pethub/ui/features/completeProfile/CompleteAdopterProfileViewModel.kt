package com.autistasgourmet.pethub.ui.features.completeProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.model.AdopterExperience
import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.domain.model.HousingType
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.domain.usecase.AdopterProfileError
import com.autistasgourmet.pethub.domain.usecase.CompleteAdopterProfileUseCase
import com.autistasgourmet.pethub.ui.util.SnackbarManager
import com.google.firebase.FirebaseNetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteAdopterProfileViewModel @Inject constructor(
    private val completeAdopterProfileUseCase: CompleteAdopterProfileUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    // estadios de los campos
    var name by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var age by mutableStateOf("")
        private set
    var occupation by mutableStateOf("")
        private set
    var postalCode by mutableStateOf("")
        private set
    var housingType by mutableStateOf(HousingType.CASA)
        private set
    var hasPatio by mutableStateOf(false)
        private set
    var spaceRoutineDetails by mutableStateOf("")
        private set
    var petExperience by mutableStateOf(AdopterExperience.PRIMERA_VEZ)
        private set
    var hasDogs by mutableStateOf(false)
        private set
    var hasCats by mutableStateOf(false)
        private set
    var hasKids by mutableStateOf(false)
        private set

    // composmisos de cuidado
    var vetVisits by mutableStateOf(false)
        private set
    var vaccination by mutableStateOf(false)
        private set
    var deworming by mutableStateOf(false)
        private set
    var properHygiene by mutableStateOf(false)
        private set
    var cleanWater by mutableStateOf(false)
        private set
    var kibbleFeeding by mutableStateOf(false)
        private set

    // estados de ui
    var isLoading by mutableStateOf(false)
        private set

    var nameError by mutableStateOf<String?>(null)
        private set
    var lastNameError by mutableStateOf<String?>(null)
        private set
    var ageError by mutableStateOf<String?>(null)
        private set
    var occupationError by mutableStateOf<String?>(null)
        private set
    var postalCodeError by mutableStateOf<String?>(null)
        private set
    var spaceDetailsError by mutableStateOf<String?>(null)
        private set

    private val _saveSuccess = MutableSharedFlow<Boolean>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    // metodos de actualizacion
    fun onNameChange(value: String) { name = value; nameError = null }
    fun onLastNameChange(value: String) { lastName = value; lastNameError = null }
    fun onAgeChange(value: String) { age = value; ageError = null }
    fun onOccupationChange(value: String) { occupation = value; occupationError = null }
    fun onPostalCodeChange(value: String) {
        if (value.length <= 5) {
            postalCode = value
            postalCodeError = null
        }
    }
    fun onHousingTypeChange(value: HousingType) { housingType = value }
    fun onHasPatioChange(value: Boolean) { hasPatio = value }
    fun onSpaceRoutineDetailsChange(value: String) { spaceRoutineDetails = value; spaceDetailsError = null }
    fun onPetExperienceChange(value: AdopterExperience) { petExperience = value }
    fun onHasDogsChange(value: Boolean) { hasDogs = value }
    fun onHasCatsChange(value: Boolean) { hasCats = value }
    fun onHasKidsChange(value: Boolean) { hasKids = value }

    fun onVetVisitsChange(value: Boolean) { vetVisits = value }
    fun onVaccinationChange(value: Boolean) { vaccination = value }
    fun onDewormingChange(value: Boolean) { deworming = value }
    fun onProperHygieneChange(value: Boolean) { properHygiene = value }
    fun onCleanWaterChange(value: Boolean) { cleanWater = value }
    fun onKibbleFeedingChange(value: Boolean) { kibbleFeeding = value }

    fun saveProfile() {
        viewModelScope.launch {
            isLoading = true
            resetErrors()

            val currentUser = authRepository.currentUser.first()
            if (currentUser == null) {
                SnackbarManager.showMessage("Inicia sesión primero")
                isLoading = false
                return@launch
            }

            val ageInt = age.toIntOrNull() ?: 0

            val profile = AdopterProfile(
                userId = currentUser.uid,
                name = name,
                lastName = lastName,
                age = ageInt,
                occupation = occupation,
                postalCode = postalCode,
                city = "",
                housingType = housingType,
                hasPatio = hasPatio,
                spaceRoutineDetails = spaceRoutineDetails,
                petExperience = petExperience,
                hasDogs = hasDogs,
                hasCats = hasCats,
                hasKids = hasKids,
                vetVisits = vetVisits,
                vaccination = vaccination,
                deworming = deworming,
                properHygiene = properHygiene,
                cleanWater = cleanWater,
                kibbleFeeding = kibbleFeeding
            )

            completeAdopterProfileUseCase(profile).onSuccess {
                _saveSuccess.emit(true)
            }.onFailure { e ->
                handleError(e)
            }
            isLoading = false
        }
    }

    private fun resetErrors() {
        nameError = null
        lastNameError = null
        ageError = null
        occupationError = null
        postalCodeError = null
        spaceDetailsError = null
    }

    private suspend fun handleError(e: Throwable) {
        when (e) {
            is AdopterProfileError.InvalidFields -> {
                e.errors.forEach { error ->
                    when (error) {
                        is AdopterProfileError.EmptyName -> nameError = "El nombre es obligatorio"
                        is AdopterProfileError.EmptyLastName -> lastNameError = "El apellido es obligatorio"
                        is AdopterProfileError.InvalidAge -> ageError = "Ingresa una edad válida"
                        is AdopterProfileError.EmptyOccupation -> occupationError = "La ocupación es obligatoria"
                        is AdopterProfileError.InvalidPostalCode -> postalCodeError = "Código postal no válido para Zacatecas"
                        is AdopterProfileError.EmptySpaceDetails -> spaceDetailsError = "Detalla el espacio y rutina"
                        else -> {}
                    }
                }
            }
            is FirebaseNetworkException -> SnackbarManager.showMessage("Error de red. Revisa tu conexión.")
            else -> SnackbarManager.showMessage(e.message ?: "Ocurrió un error inesperado")
        }
    }
}
