package com.autistasgourmet.pethub.ui.features.adopt.adoptDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.usecase.GetPetDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel @Inject constructor(
    private val getPetDetailUseCase: GetPetDetailUseCase
) : ViewModel() {

    var pet by mutableStateOf<Pet?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun loadPetDetail(petId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val result = getPetDetailUseCase(petId)
                if (result != null) {
                    pet = result
                } else {
                    errorMessage = "No se encontró la mascota seleccionada"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Error al cargar el detalle de la mascota"
            } finally {
                isLoading = false
            }
        }
    }
}