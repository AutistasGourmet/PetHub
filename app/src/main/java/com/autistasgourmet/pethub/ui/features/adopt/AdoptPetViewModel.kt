package com.autistasgourmet.pethub.ui.features.adopt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.domain.repository.PetRepository
import com.autistasgourmet.pethub.domain.usecase.ExpressInterestUseCase
import com.autistasgourmet.pethub.domain.usecase.GetAdoptablePetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdoptPetViewModel @Inject constructor(
    private val getAdoptablePetsUseCase: GetAdoptablePetsUseCase,
    private val expressInterestUseCase: ExpressInterestUseCase,
    private val authRepository: AuthRepository,
    val petRepository: PetRepository
) : ViewModel() {

    var pets by mutableStateOf<List<Pet>>(emptyList())
        private set

    var isLoading by mutableStateOf(value = false)
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
                .distinctUntilChanged()
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

    fun onLikePet(pet: Pet) {
        viewModelScope.launch {
            val user = authRepository.currentUser.firstOrNull()
            user?.let {
                expressInterestUseCase(pet.id, it.uid, true)
            }
        }
    }

    fun onDislikePet(pet: Pet) {
        viewModelScope.launch {
            val user = authRepository.currentUser.firstOrNull()
            user?.let {
                expressInterestUseCase(pet.id, it.uid, false)
            }
        }
    }
}