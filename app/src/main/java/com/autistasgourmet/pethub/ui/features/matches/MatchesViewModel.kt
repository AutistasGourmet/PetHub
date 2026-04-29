package com.autistasgourmet.pethub.ui.features.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.usecase.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
class MatchesViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchesUiState())
    val uiState: StateFlow<MatchesUiState> = _uiState.asStateFlow()

    init {
        loadMatches()
    }

    private fun loadMatches() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getMatchesUseCase()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { matches ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            matches = matches.sortedByDescending { m -> m.compatibilityScore }
                        ) 
                    }
                }
        }
    }
}
