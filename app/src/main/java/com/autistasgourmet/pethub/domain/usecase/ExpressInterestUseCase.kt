package com.autistasgourmet.pethub.domain.usecase

import android.util.Log
import com.autistasgourmet.pethub.domain.repository.PetRepository
import javax.inject.Inject

class ExpressInterestUseCase @Inject constructor(
    private val repository: PetRepository
) {
    suspend operator fun invoke(petId: String, userId: String, isInterested: Boolean): Result<Unit> {
        Log.d("ExpressInterestUseCase", "CU-06: Registrando interés - Pet: $petId, User: $userId, Interested: $isInterested")
        return repository.registerInterest(petId, userId, isInterested)
    }
}
