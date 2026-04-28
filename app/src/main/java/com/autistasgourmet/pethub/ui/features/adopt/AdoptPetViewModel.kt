package com.autistasgourmet.pethub.ui.features.adopt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.domain.usecase.GetAdoptablePetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class AdoptPetViewModel @Inject constructor(
    private val getAdoptablePetsUseCase: GetAdoptablePetsUseCase
) : ViewModel() {

    var pets by mutableStateOf<List<Pet>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadPets()
    }

    fun loadPets() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            getAdoptablePetsUseCase()
                .catch { e ->
                    errorMessage = e.message ?: "Error al cargar las mascotas"
                    isLoading = false
                }
                .collect { petList ->
                    pets = petList
                    isLoading = false
                }
        }
    }
    
    fun onStackEmpty() {
        loadPets()
    }
}
