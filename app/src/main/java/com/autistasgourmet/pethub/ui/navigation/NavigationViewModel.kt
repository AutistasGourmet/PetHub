package com.autistasgourmet.pethub.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.ui.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: AdopterProfileRepository
) : ViewModel() {

    fun validateAdoptionAccess(onAuthorized: () -> Unit) {
        viewModelScope.launch {
            val user = authRepository.currentUser.firstOrNull()
            if (user != null) {
                val profile = profileRepository.getProfile(user.uid)
                if (profile != null) {
                    onAuthorized()
                } else {
                    SnackbarManager.showMessage("Necesitas completar tu perfil de adoptante para ver las mascotas")
                }
            }
        }
    }
}
