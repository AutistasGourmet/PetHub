package com.autistasgourmet.pethub.ui.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.domain.model.User
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.domain.usecase.GetAdopterProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getAdopterProfileUseCase: GetAdopterProfileUseCase
) : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)
        private set

    var adopterProfile by mutableStateOf<AdopterProfile?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        observeUserData()
    }

    private fun observeUserData() {
        viewModelScope.launch {
            isLoading = true
            authRepository.currentUser.collectLatest { user ->
                currentUser = user
                user?.let {
                    adopterProfile = getAdopterProfileUseCase(it.uid)
                }
                isLoading = false
            }
        }
    }

    fun loadData() {
        viewModelScope.launch {
            val user = currentUser ?: return@launch
            isLoading = true
            adopterProfile = getAdopterProfileUseCase(user.uid)
            isLoading = false
        }
    }
}
