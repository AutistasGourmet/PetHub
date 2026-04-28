package com.autistasgourmet.pethub.ui.features.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.ui.components.AppSectionText
import com.autistasgourmet.pethub.ui.components.appButtons.AppClickableText
import com.autistasgourmet.pethub.ui.components.user.UserProfileCard

@Composable
fun ProfileScreen(
    onNavigateToCompleteProfile: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val adopterProfile = viewModel.adopterProfile
    val user = viewModel.currentUser
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )

        UserProfileCard(
            // PRIORIDAD: 1. Nombre Formulario, 2. Nombre Cuenta, 3. "Usuario"
            username = (adopterProfile?.name ?: user?.name).orEmpty().ifBlank { "Usuario" },
            email = user?.email ?: "animalover@ejemplo.com"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Perfil de Adoptante",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            AppClickableText(
                text = "Editar Perfil",
                onClick = onNavigateToCompleteProfile
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading && adopterProfile == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            adopterProfile != null -> {
                AdopterProfilePreviewCard(profile = adopterProfile)
            }
            else -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfilePreviewItem(
                        title = "Información del Perfil",
                        content = listOf("No has completado el perfil de Adoptante")
                    )
                }
            }
        }
    }
}

@Composable
fun AdopterProfilePreviewCard(profile: AdopterProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProfilePreviewItem(
                title = "TIPO DE VIVIENDA",
                content = listOf(
                    profile.housingType.displayName,
                    if (profile.hasPatio) "Con patio / Terraza / Jardín seguro" else "Sin patio"
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))

            ProfilePreviewItem(
                title = "EXPERIENCIA",
                content = listOf(profile.petExperience.displayName)
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))

            ProfilePreviewItem(
                title = "OTRAS MASCOTAS",
                content = listOf(
                    "Perros: ${if (profile.hasDogs) "Sí" else "No"}",
                    "Gatos: ${if (profile.hasCats) "Sí" else "No"}"
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))

            ProfilePreviewItem(
                title = "UBICACIÓN",
                content = listOf(
                    profile.city.ifBlank { "Ciudad no especificada" },
                    "Zacatecas"
                )
            )
        }
    }
}

@Composable
fun ProfilePreviewItem(title: String, content: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        content.forEach { text ->
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
