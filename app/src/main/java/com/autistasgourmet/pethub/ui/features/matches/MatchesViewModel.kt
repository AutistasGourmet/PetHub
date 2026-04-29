package com.autistasgourmet.pethub.ui.features.matches

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MatchItemUiState(
    val userId: String,
    val fullName: String,
    val interestedInPetName: String,
    val location: String,
    val description: String,
    val compatibilityScore: Int = 0 // Usado para ordenar (FA-1)
)

data class MatchesUiState(
    val isLoading: Boolean = false,
    val matches: List<MatchItemUiState> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class MatchesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MatchesUiState())
    val uiState: StateFlow<MatchesUiState> = _uiState.asStateFlow()

    init {
        loadMockMatches()
    }

    private fun loadMockMatches() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        
        // Datos mockeados basados en las propiedades del perfil del adoptante
        val mockMatches = listOf(
            MatchItemUiState(
                userId = "user_1",
                fullName = "Ángel Segovia",
                interestedInPetName = "Toby",
                location = "Guadalupe, Zac.",
                description = "Apartamento, Tengo Mascotas Actualmente",
                compatibilityScore = 85
            ),
            MatchItemUiState(
                userId = "user_2",
                fullName = "Luis Gutiérrez",
                interestedInPetName = "Toby",
                location = "Guadalupe, Zac.",
                description = "Apartamento, Tengo Mascotas Actualmente",
                compatibilityScore = 90
            ),
            MatchItemUiState(
                userId = "user_3",
                fullName = "Sergio González",
                interestedInPetName = "Toby",
                location = "Zacatecas, Zac.",
                description = "Casa con patio, Sin Mascotas Actualmente",
                compatibilityScore = 75
            )
        )
        
        // Ordenamos por defecto por compatibilidad
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            matches = mockMatches.sortedByDescending { it.compatibilityScore }
        )
    }

    // Funcionalidad para probar la excepción E1 (Bandeja Vacía)
    fun clearMatches() {
        _uiState.value = _uiState.value.copy(matches = emptyList())
    }
}
