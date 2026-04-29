package com.autistasgourmet.pethub.ui.components.appCards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@Composable
fun CandidateDetailsCard(
    housingType: String,
    patioDetails: String,
    kidsDetails: String,
    experienceDetails: String,
    otherPetsDogs: String,
    otherPetsCats: String,
    routineDetails: String,
    careDetails: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailSection(
                title = "TIPO DE VIVIENDA",
                lines = listOf(housingType, patioDetails)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            DetailSection(
                title = "HIJOS",
                lines = listOf(kidsDetails)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            DetailSection(
                title = "EXPERIENCIA",
                lines = listOf(experienceDetails)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            DetailSection(
                title = "OTRAS MASCOTAS",
                lines = listOf(otherPetsDogs, otherPetsCats)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            DetailSection(
                title = "DETALLES SOBRE ESPACIO Y RUTINA",
                lines = listOf(routineDetails)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            DetailSection(
                title = "CUIDADOS QUE ESTÁ DISPUESTO A DAR",
                lines = careDetails
            )
        }
    }
}

@Composable
private fun DetailSection(title: String, lines: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        lines.forEach { line ->
            if (line.isNotBlank()) {
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CandidateDetailsCardPreview() {
    PetHubTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CandidateDetailsCard(
                housingType = "Apartamento",
                patioDetails = "Sin patio / Terraza / Jardín",
                kidsDetails = "Tengo Hijos en Casa: No",
                experienceDetails = "Tengo Mascotas Actualmente",
                otherPetsDogs = "Perros: No",
                otherPetsCats = "Gatos: No",
                routineDetails = "Trabajo de lunes a viernes solamente por la mañana y desde casa, por lo que tengo tiempo para cuidar de una mascota.",
                careDetails = listOf(
                    "Visitas al veterinario: Si",
                    "Vacunación: Si",
                    "Desparasitación: Si"
                )
            )
        }
    }
}