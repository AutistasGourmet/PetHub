package com.autistasgourmet.pethub.ui.features.adopt.adoptDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.ui.components.appChips.AppTraitChip
import com.autistasgourmet.pethub.ui.components.appChips.AppTraitChipType
import com.autistasgourmet.pethub.ui.components.appCards.AppHealthBox
import com.autistasgourmet.pethub.ui.components.media.AppPhotoPager
import com.autistasgourmet.pethub.ui.components.appCards.AppSociabilityBox
import androidx.compose.ui.tooling.preview.Preview
import com.autistasgourmet.pethub.domain.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailScreen(
    petId: String,
    onBack: () -> Unit,
    viewModel: PetDetailViewModel = hiltViewModel()
) {
    val pet = viewModel.pet
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    LaunchedEffect(petId) {
        viewModel.loadPetDetail(petId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (pet != null) "Perfil de ${pet.name}" else "Cargando...",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Surface(
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = Color(0xFFE5E7EB),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar",
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack) {
                            Text("Volver a exploración")
                        }
                    }
                }
                pet != null -> {
                    PetDetailContent(pet = pet)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetDetailContent(pet: Pet) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Image Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFFE5E7EB))
        ) {
            AppPhotoPager(
                photos = pet.photos,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            // Header: Name, Age, Energy Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${pet.name},",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = pet.ageRange.displayName,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nivel de Energía: ${pet.energyLevel.displayName}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                // Energy Chip
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFD1FAE5), // Light Green
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981)),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = null,
                            tint = Color(0xFF047857),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = pet.energyLevel.displayName,
                            color = Color(0xFF047857),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Surface(
                color = Color(0xFFE5E7EB),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${pet.city}, Zac.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Specie, Size, Gender
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppTraitChip(text = pet.species.displayName, type = AppTraitChipType.SPECIES)
                AppTraitChip(text = pet.size.displayName, type = AppTraitChipType.SIZE)
                AppTraitChip(text = pet.gender.displayName, type = AppTraitChipType.GENDER)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Acerca de Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Acerca de ${pet.name}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = pet.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estado de Salud Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.MedicalServices, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Estado de Salud",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AppHealthBox(label = "Vacunado", isTrue = pet.isVaccinated, modifier = Modifier.weight(1f))
                        AppHealthBox(label = "Desparasitado", isTrue = pet.isDewormed, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    AppHealthBox(label = "Esterilizado", isTrue = pet.isSterilized, modifier = Modifier.fillMaxWidth(0.5f).padding(end = 4.dp))
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        color = Color(0xFFFFE4E6), // Pink 100
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF43F5E)), // Pink 500
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Condiciones Especiales: ${pet.specialConditions.ifBlank { "Ninguna" }}",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sociability Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Comportamiento y Sociabilidad",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppSociabilityBox(label = "Niños", value = pet.sociabilityKids.displayName, modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        AppSociabilityBox(label = "Perros", value = pet.sociabilityDogs.displayName, modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        AppSociabilityBox(label = "Gatos", value = pet.sociabilityCats.displayName, modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Traits Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Pets, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Rasgos de Personalidad",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        pet.traits.forEach { trait ->
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFF3E8FF), // Purple 100
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA855F7)) // Purple 500
                            ) {
                                Text(
                                    text = trait.displayName,
                                    color = Color(0xFFA855F7), // Purple 500
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetDetailContentPreview() {
    MaterialTheme {
        PetDetailContent(
            pet = Pet(
                id = "1",
                ownerEmail = "test@test.com",
                name = "Max",
                species = PetSpecies.PERRO,
                ageRange = PetAgeRange.JOVEN,
                size = PetSize.MEDIANO,
                gender = PetGender.MACHO,
                energyLevel = EnergyLevel.MEDIO,
                sociabilityKids = SociabilityLevel.EXCELENTE,
                sociabilityDogs = SociabilityLevel.BUENA,
                sociabilityCats = SociabilityLevel.MALA,
                traits = listOf(PetTrait.SOCIABLE, PetTrait.JUGUETON, PetTrait.TRAVIESO, PetTrait.OBSERVADOR),
                photos = emptyList(),
                description = "Max fue encontrado vagando cerca de un parque... Es un perro muy tranquilo.",
                isVaccinated = true,
                isSterilized = true,
                isDewormed = false,
                specialConditions = "",
                postalCode = "98000",
                city = "Guadalupe"
            )
        )
    }
}
