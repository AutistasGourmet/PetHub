package com.autistasgourmet.pethub.domain.usecase

import android.util.Log
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.domain.repository.PetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAdoptablePetsUseCase @Inject constructor(
    private val repository: PetRepository,
    private val authRepository: AuthRepository,
    private val profileRepository: AdopterProfileRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Pet>> {
        return authRepository.currentUser.flatMapLatest { user ->
            if (user == null) return@flatMapLatest flowOf(emptyList())

            val profileFlow = flow { emit(profileRepository.getProfile(user.uid)) }

            combine(
                repository.getPets(),
                repository.getUserInterests(user.uid),
                profileFlow
            ) { allPets, interests, profile ->
                Log.d("GetAdoptablePetsUseCase", "CU-04: Procesando mascotas compatibles para ${user.email}")
                
                val filteredPets = allPets.filter { pet ->
                    // excluir mascotas propias
                    val isOwnPet = pet.ownerEmail == user.email
                    // excluir mascotas ya calificadas
                    val isAlreadySeen = interests.contains(pet.id)
                    
                    // filtro estricto de sociabilidad
                    val passesStrictFilter = profile?.let { p ->
                        val kidsConflict = p.hasKids && (pet.sociabilityKids == SociabilityLevel.MALA)
                        val dogsConflict = p.hasDogs && (pet.sociabilityDogs == SociabilityLevel.MALA)
                        val catsConflict = p.hasCats && (pet.sociabilityCats == SociabilityLevel.MALA)
                        
                        !(kidsConflict || dogsConflict || catsConflict)
                    } ?: true

                    !isOwnPet && !isAlreadySeen && passesStrictFilter
                }

                // Puntaje de relevancia
                filteredPets.map { pet ->
                    var score = 0
                    profile?.let { p ->
                        // la ciudad (+10)
                        if (pet.city.equals(p.city, ignoreCase = true)) score += 10
                        
                        // el tamaño vs vivienda (+5)
                        if (p.housingType == HousingType.APARTAMENTO && pet.size == PetSize.CHICO) score += 5
                        if (p.housingType == HousingType.CASA && (pet.size == PetSize.MEDIANO || pet.size == PetSize.GRANDE)) score += 5
                        
                        // la energía (+5)
                        if (p.hasPatio && pet.energyLevel == EnergyLevel.ALTO) score += 5
                    }
                    pet to score
                }
                .sortedByDescending { it.second }
                .also { list ->
                    list.forEach { (pet, score) ->
                        Log.d("GetAdoptablePetsUseCase", "Mascota: ${pet.name}, Score: $score")
                    }
                }
                .map { it.first }
            }
        }
    }
}
