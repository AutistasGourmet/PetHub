package com.autistasgourmet.pethub.ui.components.appCards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.ui.components.appChips.AppTraitChip
import com.autistasgourmet.pethub.ui.components.appChips.AppTraitChipType

@Composable
fun AdoptPetCard(
    pet: Pet,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFE5E7EB))
            ) {
                if (pet.photos.isNotEmpty()) {
                    AsyncImage(
                        model = pet.photos.first(),
                        contentDescription = "Foto de ${pet.name}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Fallback to placeholder if no photos
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.Center),
                        tint = Color.Gray.copy(alpha = 0.5f)
                    )
                }
                
                Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(percent = 50),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${pet.city}, Zac.",
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "${pet.name},",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = pet.ageRange.displayName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Nivel de Energía: ${pet.energyLevel.displayName}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AppTraitChip(text = pet.species.displayName, type = AppTraitChipType.SPECIES)
                    AppTraitChip(text = pet.size.displayName, type = AppTraitChipType.SIZE)
                    AppTraitChip(text = pet.gender.displayName, type = AppTraitChipType.GENDER)
                }

                Spacer(modifier = Modifier.height(8.dp))

                val traitsText = pet.traits.take(3).joinToString(separator = ", ") { it.displayName }
                Text(
                    text = traitsText.ifEmpty { "Sin características definidas" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdoptPetCardPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp).fillMaxWidth().height(600.dp)) {
            AdoptPetCard(
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
                    description = "Un perro muy amigable",
                    isVaccinated = true,
                    isSterilized = true,
                    isDewormed = true,
                    specialConditions = "",
                    postalCode = "98000",
                    city = "Zacatecas"
                )
            )
        }
    }
}
