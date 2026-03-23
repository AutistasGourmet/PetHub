package com.autistasgourmet.pethub.ui.features.registerPet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.domain.usecase.SavePetError
import com.autistasgourmet.pethub.domain.usecase.SavePetUseCase
import com.autistasgourmet.pethub.ui.util.SnackbarManager
import com.google.firebase.FirebaseNetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPetViewModel @Inject constructor(
    private val savePetUseCase: SavePetUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    // estados de los campo de un Pet
    var name by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var postalCode by mutableStateOf("")
        private set
    var species by mutableStateOf(PetSpecies.PERRO)
        private set
    var ageRange by mutableStateOf(PetAgeRange.CACHORRO)
        private set
    var size by mutableStateOf(PetSize.MEDIANO)
        private set
    var gender by mutableStateOf(PetGender.MACHO)
        private set
    var energyLevel by mutableStateOf(EnergyLevel.MEDIO)
        private set
    var specialConditions by mutableStateOf("")
        private set

    // listas fotos y rasgos
    var selectedPhotos by mutableStateOf<List<String>>(emptyList())
        private set
    var affectionTraits by mutableStateOf<List<String>>(emptyList())
        private set

    // estados de sociabilidad
    var sociabilityKids by mutableStateOf(SociabilityLevel.DESCONOCIDA)
        private set
    var sociabilityDogs by mutableStateOf(SociabilityLevel.DESCONOCIDA)
        private set
    var sociabilityCats by mutableStateOf(SociabilityLevel.DESCONOCIDA) // Agregado
        private set

    // listas de rasgos
    var energyTraits by mutableStateOf<List<String>>(emptyList())
        private set
    var intelligenceTraits by mutableStateOf<List<String>>(emptyList())
        private set
    var instinctTraits by mutableStateOf<List<String>>(emptyList()) // Agregado
        private set


    // estados para checklist
    var isVaccinated by mutableStateOf(false)
        private set
    var isSterilized by mutableStateOf(false)
        private set
    var isDewormed by mutableStateOf(false)
        private set

    // estados de ui
    var isLoading by mutableStateOf(false)
        private set

    var nameError by mutableStateOf<String?>(null)
        private set
    var descriptionError by mutableStateOf<String?>(null)
        private set
    var postalCodeError by mutableStateOf<String?>(null)
        private set

    private val _saveSuccess = MutableSharedFlow<Boolean>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    // metodos de actualizacion de campos
    fun onNameChange(newValue: String) { name = newValue; nameError = null }
    fun onDescriptionChange(newValue: String) { description = newValue; descriptionError = null }

    fun onPostalCodeChange(newValue: String) {
        if (newValue.length <= 5) {
            postalCode = newValue
            postalCodeError = null
        }
    }
    fun onSpecialConditionsChange(newValue: String) { specialConditions = newValue }

    fun toggleAffectionTrait(trait: String) {
        affectionTraits = if (affectionTraits.contains(trait)) affectionTraits - trait else affectionTraits + trait
    }

    fun onSociabilityKidsChange(level: SociabilityLevel) { sociabilityKids = level }
    fun onSociabilityDogsChange(level: SociabilityLevel) { sociabilityDogs = level }
    fun onSociabilityCatsChange(level: SociabilityLevel) { sociabilityCats = level } // Agregado

    fun toggleEnergyTrait(trait: String) {
        energyTraits = if (energyTraits.contains(trait)) energyTraits - trait else energyTraits + trait
    }
    fun toggleIntelligenceTrait(trait: String) {
        intelligenceTraits = if (intelligenceTraits.contains(trait)) intelligenceTraits - trait else intelligenceTraits + trait
    }
    fun toggleInstinctTrait(trait: String) { // Agregado
        instinctTraits = if (instinctTraits.contains(trait)) instinctTraits - trait else instinctTraits + trait
    }

    // metodos para listas enum y ckecklist
    fun onSpeciesChange(newSpecies: PetSpecies) { species = newSpecies }
    fun onAgeRangeChange(newAge: PetAgeRange) { ageRange = newAge }
    fun onSizeChange(newSize: PetSize) { size = newSize }
    fun onGenderChange(newGender: PetGender) { gender = newGender }
    fun onEnergyLevelChange(newLevel: EnergyLevel) { energyLevel = newLevel }
    fun onVaccinatedChange(value: Boolean) { isVaccinated = value }
    fun onSterilizedChange(value: Boolean) { isSterilized = value }
    fun onDewormedChange(value: Boolean) { isDewormed = value }

    fun addPhoto(url: String) {
        selectedPhotos = selectedPhotos + url
    }

    fun removePhoto(url: String) {
        selectedPhotos = selectedPhotos - url
    }

    fun savePet() {
        viewModelScope.launch {
            isLoading = true
            resetErrors()

            val currentUser = authRepository.currentUser.first()
            if (currentUser == null) {
                SnackbarManager.showMessage("Inicia sesión primero")
                isLoading = false
                return@launch
            }

            val pet = Pet(
                ownerEmail = currentUser.email,
                name = name,
                species = species,
                ageRange = ageRange,
                size = size,
                gender = gender,
                energyLevel = energyLevel,
                sociabilityKids = sociabilityKids,
                sociabilityDogs = sociabilityDogs,
                sociabilityCats = sociabilityCats,
                affectionTraits = affectionTraits,
                energyTraits = energyTraits,
                intelligenceTraits = intelligenceTraits,
                instinctTraits = instinctTraits,
                photos = selectedPhotos,
                description = description,
                isVaccinated = isVaccinated,
                isSterilized = isSterilized,
                isDewormed = isDewormed,
                specialConditions = specialConditions,
                postalCode = postalCode,
                city = ""
            )

            savePetUseCase(pet).onSuccess {
                _saveSuccess.emit(true)
            }.onFailure { e ->
                handleError(e)
            }
            isLoading = false
        }
    }

    private fun resetErrors() {
        nameError = null
        descriptionError = null
        postalCodeError = null
    }

    private suspend fun handleError(e: Throwable) {
        when (e) {
            is SavePetError.InvalidFields -> {
                e.errors.forEach { error ->
                    when (error) {
                        is SavePetError.EmptyName -> nameError = "El nombre es obligatorio"
                        is SavePetError.EmptyDescription -> descriptionError = "Añade una descripción"
                        is SavePetError.NoPhotos -> SnackbarManager.showMessage("Debes subir al menos una foto")
                        is SavePetError.InvalidPostalCode -> postalCodeError = "Código postal no válido para Zacatecas"
                        else -> {}
                    }
                }
            }
            is FirebaseNetworkException -> SnackbarManager.showMessage("Error de red. Revisa tu conexión.")
            else -> SnackbarManager.showMessage(e.message ?: "Ocurrió un error inesperado")
        }
    }
}