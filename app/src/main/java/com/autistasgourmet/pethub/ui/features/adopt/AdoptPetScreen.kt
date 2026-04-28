package com.autistasgourmet.pethub.ui.features.adopt

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.autistasgourmet.pethub.ui.components.appCards.SwipeableCard
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.ui.components.appCards.AdoptPetCard

@Composable
fun AdoptPetScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: AdoptPetViewModel = hiltViewModel()
) {
    val pets = viewModel.pets
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    AdoptPetScreenContent(
        pets = pets,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onNavigateToDetail = onNavigateToDetail
    )
}

@Composable
fun AdoptPetScreenContent(
    pets: List<Pet>,
    isLoading: Boolean,
    errorMessage: String?,
    onNavigateToDetail: (String) -> Unit
) {
    val currentPets = remember(pets) { mutableStateListOf(*pets.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mascotas en Adopción",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
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
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    currentPets.asReversed().forEach { pet ->
                        key(pet.id) {
                            SwipeableCard(
                                onSwipeLeft = { currentPets.remove(pet) },
                                onSwipeRight = { currentPets.remove(pet) }
                            ) {
                                AdoptPetCard(pet = pet)
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
            onNavigateToDetail = {}
        )
    }
}
