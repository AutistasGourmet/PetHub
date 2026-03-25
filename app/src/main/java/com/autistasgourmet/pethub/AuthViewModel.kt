package com.autistasgourmet.pethub

import androidx.lifecycle.ViewModel
import com.autistasgourmet.pethub.domain.model.User
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val currentUser: Flow<User?> = authRepository.currentUser
}
