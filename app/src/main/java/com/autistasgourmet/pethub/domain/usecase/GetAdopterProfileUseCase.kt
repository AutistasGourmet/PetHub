package com.autistasgourmet.pethub.domain.usecase

import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository
import javax.inject.Inject

class GetAdopterProfileUseCase @Inject constructor(
    private val repository: AdopterProfileRepository
) {
    suspend operator fun invoke(userId: String): AdopterProfile? {
        return repository.getProfile(userId)
    }
}
