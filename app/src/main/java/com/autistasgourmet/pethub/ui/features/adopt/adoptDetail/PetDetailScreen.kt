package com.autistasgourmet.pethub.ui.features.adopt.adoptDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.ui.components.appChips.AppTraitChip
import com.autistasgourmet.pethub.ui.components.appChips.AppTraitChipType
import com.autistasgourmet.pethub.ui.components.appCards.AppHealthBox
import com.autistasgourmet.pethub.ui.components.media.AppPhotoPager
import com.autistasgourmet.pethub.ui.components.appCards.AppSociabilityBox
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.autistasgourmet.pethub.domain.model.*

@Composable
fun PetDetailScreen(
    petId: String,
    viewModel: PetDetailViewModel = hiltViewModel()
) {
    val pet = viewModel.pet
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    LaunchedEffect(petId) {
        viewModel.loadPetDetail(petId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            errorMessage != null -> Text(text = errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            pet != null -> PetDetailContent(pet = pet, petRepository = viewModel.petRepository)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetDetailContent(
    pet: Pet,
    petRepository: com.autistasgourmet.pethub.domain.repository.PetRepository
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Fotos al inicio
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFFE5E7EB))
        ) {
            AppPhotoPager(
                photos = pet.photos,
                petRepository = petRepository,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${pet.name},",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = pet.ageRange.displayName,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 2.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nivel de Energía: ${pet.energyLevel.displayName}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Energy Chip
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFD1FAE5),
                    border = BorderStroke(1.dp, Color(0xFF10B981)),
                    modifier = Modifier.padding(top = 4.dp).padding(start = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = null,
                            tint = Color(0xFF047857),
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = pet.energyLevel.displayName,
                            color = Color(0xFF047857),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ubicación
            Surface(color = Color(0xFFE5E7EB), shape = RoundedCornerShape(16.dp)) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${pet.city}, Zac.", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppTraitChip(text = pet.species.displayName, type = AppTraitChipType.SPECIES)
                AppTraitChip(text = pet.size.displayName, type = AppTraitChipType.SIZE)
                AppTraitChip(text = pet.gender.displayName, type = AppTraitChipType.GENDER)
            }

            Spacer(modifier = Modifier.height(24.dp))

            DetailCard(icon = Icons.Outlined.Info, title = "Acerca de ${pet.name}") {
                Text(text = pet.description, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estado de Salud
            DetailCard(icon = Icons.Default.MedicalServices, title = "Estado de Salud") {
                Column {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (pet.isVaccinated) AppHealthBox(label = "Vacunado")
                        if (pet.isDewormed) AppHealthBox(label = "Desparasitado")
                        if (pet.isSterilized) AppHealthBox(label = "Esterilizado")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cuadro rosa de Condiciones Especiales
                    Surface(
                        color = Color(0xFFFFE4E6),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFF43F5E)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Condiciones Especiales: ${pet.specialConditions.ifBlank { "Ninguna." }}",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DetailCard(icon = Icons.Default.FavoriteBorder, title = "Comportamiento y Sociabilidad") {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    AppSociabilityBox(label = "Niños", value = pet.sociabilityKids.displayName, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    AppSociabilityBox(label = "Perros", value = pet.sociabilityDogs.displayName, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    AppSociabilityBox(label = "Gatos", value = pet.sociabilityCats.displayName, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DetailCard(icon = Icons.Default.Pets, title = "Rasgos de Personalidad") {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pet.traits.forEach { trait ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFF3E8FF),
                            border = BorderStroke(1.dp, Color(0xFFA855F7))
                        ) {
                            Text(text = trait.displayName, color = Color(0xFFA855F7), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun DetailCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetDetailPreview() {
    MaterialTheme {
        PetDetailContent(
            pet = Pet(
                id = "1",
                ownerEmail = "test@test.com",
                name = "Firulais",
                species = PetSpecies.PERRO,
                ageRange = PetAgeRange.JOVEN,
                size = PetSize.MEDIANO,
                gender = PetGender.MACHO,
                energyLevel = EnergyLevel.ALTO,
                sociabilityKids = SociabilityLevel.BUENA,
                sociabilityDogs = SociabilityLevel.BUENA,
                sociabilityCats = SociabilityLevel.MALA,
                traits = listOf(PetTrait.JUGUETON, PetTrait.CARINOSO, PetTrait.ACTIVO),
                photos = emptyList(),
                description = "Un perro muy amigable y juguetón que busca un hogar lleno de amor.",
                isVaccinated = true,
                isSterilized = true,
                isDewormed = true,
                specialConditions = "",
                postalCode = "98000",
                city = "Zacatecas"
            ),
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
