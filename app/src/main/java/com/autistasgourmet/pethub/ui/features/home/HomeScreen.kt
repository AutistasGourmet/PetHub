package com.autistasgourmet.pethub.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.autistasgourmet.pethub.ui.components.AppCheckBox
import com.autistasgourmet.pethub.ui.components.AppDropdown
import com.autistasgourmet.pethub.ui.components.AppSectionText
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton
import com.autistasgourmet.pethub.ui.components.appChips.AppChoiceChips
import com.autistasgourmet.pethub.ui.components.appChips.AppFilterChips
import com.autistasgourmet.pethub.ui.components.appFields.AppNumberField
import com.autistasgourmet.pethub.ui.components.appFields.AppTextArea
import com.autistasgourmet.pethub.ui.components.appFields.AppTextField

@Composable
fun HomeScreen(
    onNavigateToAdopt: () -> Unit,
    onNavigateToPublish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Column {
            Text(
                text = "Bienvenido, Animal Lover",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "¿Qué te gustaría hacer hoy?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuickAccessCard(
                title = "Adoptar Mascota",
                description = "Encuentra tu mascota perfecta",
                icon = Icons.Default.FavoriteBorder,
                iconContainerColor = Color.Magenta.copy(alpha = 0.1f),
                iconColor = Color.Magenta,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToAdopt
            )
            QuickAccessCard(
                title = "Publicar Mascota",
                description = "Publica una mascota para su adopción",
                icon = Icons.Outlined.AddBox,
                iconContainerColor = Color.Blue.copy(alpha = 0.1f),
                iconColor = Color.Blue,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToPublish
            )
        }

        // los componentes
        AppSectionText(title = "Boton") {
            AppPrimaryButton(
                text = "Iniciar Sesión",
                onClick = { /* acción */ },
                icon = Icons.AutoMirrored.Filled.Login
            )
        }

        var selectedEnergy by remember { mutableStateOf("Medio") }
        val traitOptions = listOf(
            "Juguetón", "Tímido", "Independiente", "Guardián",
            "Faldero", "Cariñoso", "Tranquilo", "Activo"
        )
        var selectedFilters by remember { mutableStateOf(listOf("Timido")) }
        AppSectionText(title = "Selección (Chips)") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AppChoiceChips(
                    label = "Nivel de Energía",
                    options = listOf("Bajo", "Medio", "Alto"),
                    selectedOption = selectedEnergy,
                    onOptionSelected = { selectedEnergy = it }
                )

                AppFilterChips(
                    label = "Rasgos de su personalidad",
                    options = traitOptions,
                    selectedOptions = selectedFilters,
                    onOptionToggled = { option ->
                        selectedFilters = if (selectedFilters.contains(option)) {
                            selectedFilters - option
                        } else {
                            selectedFilters + option
                        }
                    }
                )
            }
        }

        var userName by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var userDescription by remember {
            mutableStateOf(
                "Trabajo desde casa 3 días a la semana, " +
                        "por lo que tengo tiempo para dedicar a una " +
                        "mascota. Me gusta dar paseos largos."
            )
        }
        AppSectionText(title = "Campos de Entrada") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AppTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = "Nombre De Usuario",
                    placeholder = "Escribe tu usuario",
                    leadingIcon = Icons.Default.Person
                )

                AppNumberField(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    value = age,
                    onValueChange = { age = it },
                    label = "Edad",
                    placeholder = "tu edad",
                )

                AppTextArea(
                    value = userDescription,
                    onValueChange = { userDescription = it },
                    label = "Detalles sobre el espacio y rutina",
                    placeholder = "Escribe tus detalles"
                )
            }
        }

        var isCheckedVac by remember { mutableStateOf(false) }
        var isCheckedEst by remember { mutableStateOf(false) }
        AppSectionText(title = "Validaciones (Checkboxes)") {
            Column {
                AppCheckBox(
                    label = "Vacunado",
                    checked = isCheckedVac,
                    onCheckedChange = { isCheckedVac = it }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                AppCheckBox(
                    label = "Esterilizado / Castrado",
                    checked = isCheckedEst,
                    onCheckedChange = { isCheckedEst = it }
                )
            }
        }

        var selectedSpecies by remember { mutableStateOf("Perro") }
        AppSectionText(title = "Selectores (Dropdown)") {
            AppDropdown(
                label = "Especie",
                options = listOf("Perro", "Gato", "Otro"),
                selectedOption = selectedSpecies,
                onOptionSelected = { selectedSpecies = it }
            )
        }
    }
}

@Composable
fun QuickAccessCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconContainerColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconContainerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconColor
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
