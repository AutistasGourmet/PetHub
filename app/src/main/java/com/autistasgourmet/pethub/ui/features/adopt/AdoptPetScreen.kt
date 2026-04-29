package com.autistasgourmet.pethub.ui.features.adopt

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.autistasgourmet.pethub.ui.components.appCards.SwipeableCard
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.ui.components.appCards.AdoptPetCard

@Composable
fun AdoptPetScreen(
    onNavigateToDetail: (String, String) -> Unit,
    viewModel: AdoptPetViewModel = hiltViewModel(),
) {
    val pets = viewModel.pets
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    AdoptPetScreenContent(
        pets = pets,
        isLoading = isLoading,
        errorMessage = errorMessage,
        petRepository = viewModel.petRepository,
        onNavigateToDetail = onNavigateToDetail,
        onLikePet = viewModel::onLikePet,
        onDislikePet = viewModel::onDislikePet
    )
}

@Composable
fun AdoptPetScreenContent(
    pets: List<Pet>,
    isLoading: Boolean,
    errorMessage: String?,
    petRepository: com.autistasgourmet.pethub.domain.repository.PetRepository,
    onNavigateToDetail: (String, String) -> Unit,
    onLikePet: (Pet) -> Unit,
    onDislikePet: (Pet) -> Unit
) {
    val currentPets = remember(pets.map { it.id }) { mutableStateListOf(*pets.toTypedArray()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mascotas en Adopción",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Error de conexión: $errorMessage",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                currentPets.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No hay mascotas por mostrar en este momento.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        currentPets.asReversed().forEach { pet ->
                            key(pet.id) {
                                SwipeableCard(
                                    // CU-04: solo se navega a la siguiente mascota (remover)
                                    onSwipeLeft = {
                                        currentPets.remove(pet) 
                                    },
                                    onSwipeRight = { 
                                        currentPets.remove(pet)
                                    }
                                ) {
                                    AdoptPetCard(
                                        pet = pet,
                                        petRepository = petRepository,
                                        modifier = Modifier.fillMaxSize(),
                                    ) { onNavigateToDetail(pet.id, pet.name) }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val currentPet = currentPets.firstOrNull()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            onClick = { 
                                currentPet?.let { 
                                    onDislikePet(it)
                                    currentPets.remove(it) 
                                } 
                            },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.errorContainer,
                            shadowElevation = 8.dp,
                            modifier = Modifier.size(80.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Falta de interés",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }

                        Surface(
                            onClick = { 
                                currentPet?.let { 
                                    onLikePet(it)
                                    currentPets.remove(it) 
                                } 
                            },
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shadowElevation = 8.dp,
                            modifier = Modifier.size(80.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Expresar interés",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdoptPetScreenPreview() {
    MaterialTheme {
        AdoptPetScreenContent(
            pets = listOf(
                Pet(
                    id = "1",
                    ownerEmail = "test@test.com",
                    name = "Luna",
                    species = PetSpecies.PERRO,
                    ageRange = PetAgeRange.CACHORRO,
                    size = PetSize.CHICO,
                    gender = PetGender.HEMBRA,
                    energyLevel = EnergyLevel.ALTO,
                    sociabilityKids = SociabilityLevel.BUENA,
                    sociabilityDogs = SociabilityLevel.EXCELENTE,
                    sociabilityCats = SociabilityLevel.DESCONOCIDA,
                    traits = listOf(PetTrait.JUGUETON, PetTrait.CURIOSO),
                    photos = emptyList(),
                    description = "Una cachorrita muy linda",
                    isVaccinated = false,
                    isSterilized = false,
                    isDewormed = true,
                    specialConditions = "Ninguna",
                    postalCode = "98000",
                    city = "Zacatecas"
                )
            ),
            isLoading = false,
            errorMessage = null,
            onNavigateToDetail = { _, _ -> },
            onLikePet = {},
            onDislikePet = {},
            petRepository = object : com.autistasgourmet.pethub.domain.repository.PetRepository {
                override fun getPets() = kotlinx.coroutines.flow.flowOf(emptyList<Pet>())
                override fun getPetsByOwner(ownerEmail: String) = kotlinx.coroutines.flow.flowOf(emptyList<Pet>())
                override suspend fun getPetById(id: String) = null
                override suspend fun savePet(pet: Pet) = Result.success(Unit)
                override suspend fun deletePet(id: String) = Result.success(Unit)
                override suspend fun savePetPhoto(base64: String) = Result.success("")
                override suspend fun getPetPhoto(photoId: String) = Result.success("")
                override suspend fun registerInterest(petId: String, userId: String, isInterested: Boolean) = Result.success(Unit)
                override fun getUserInterests(userId: String) = kotlinx.coroutines.flow.flowOf(emptyList<String>())
            }
        )
    }
}
