package com.autistasgourmet.pethub.domain.usecase

import android.util.Log
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.domain.repository.PetRepository
import com.autistasgourmet.pethub.ui.features.matches.MatchItemUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val petRepository: PetRepository,
    private val adopterProfileRepository: AdopterProfileRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<MatchItemUiState>> {
        return authRepository.currentUser.flatMapLatest { user ->
            if (user == null) {
                Log.d("GetMatchesUseCase", "Usuario nulo, devolviendo lista vacía")
                return@flatMapLatest flowOf(emptyList())
            }

            Log.d("GetMatchesUseCase", "Buscando mascotas para el dueño: ${user.email}")
            petRepository.getPetsByOwner(user.email).flatMapLatest { myPets ->
                if (myPets.isEmpty()) {
                    Log.d("GetMatchesUseCase", "El usuario no tiene mascotas publicadas")
                    return@flatMapLatest flowOf(emptyList())
                }

                Log.d("GetMatchesUseCase", "Mascotas encontradas: ${myPets.map { it.name }}")
                
                val petInterestFlows = myPets.map { pet ->
                    petRepository.getInterestsForPet(pet.id).map { userIds ->
                        Log.d("GetMatchesUseCase", "Interesados para ${pet.name} (${pet.id}): $userIds")
                        userIds.map { userId -> pet to userId }
                    }
                }
                
                if (petInterestFlows.isEmpty()) return@flatMapLatest flowOf(emptyList())
                
                combine(petInterestFlows) { allPetUserPairs ->
                    allPetUserPairs.toList().flatten()
                }.flatMapLatest { petUserPairs ->
                    if (petUserPairs.isEmpty()) {
                        Log.d("GetMatchesUseCase", "No hay intereses registrados para ninguna mascota")
                        return@flatMapLatest flowOf(emptyList())
                    }
                    
                    Log.d("GetMatchesUseCase", "Procesando perfiles para ${petUserPairs.size} matches")
                    
                    flow {
                        val matches = petUserPairs.mapNotNull { (pet, userId) ->
                            val profile = adopterProfileRepository.getProfile(userId)
                            if (profile != null) {
                                Log.d("GetMatchesUseCase", "Perfil encontrado para el interesado $userId: ${profile.name}")
                                MatchItemUiState(
                                    userId = userId,
                                    fullName = "${profile.name} ${profile.lastName}",
                                    interestedInPetName = pet.name,
                                    location = "${profile.municipality.displayName}, Zac.",
                                    description = "${profile.housingType.displayName}, ${profile.petExperience.displayName}"
                                )
                            } else {
                                Log.d("GetMatchesUseCase", "No se encontró perfil de adoptante para el usuario interesado: $userId")
                                null
                            }
                        }
                        emit(matches)
                    }
                }
            }
        }
    }
}
