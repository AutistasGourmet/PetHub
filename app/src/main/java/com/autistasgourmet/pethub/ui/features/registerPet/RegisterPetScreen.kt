package com.autistasgourmet.pethub.ui.features.registerPet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autistasgourmet.pethub.domain.model.*
import com.autistasgourmet.pethub.ui.components.AppCheckBox
import com.autistasgourmet.pethub.ui.components.AppDropdown
import com.autistasgourmet.pethub.ui.components.AppHighlightedText
import com.autistasgourmet.pethub.ui.components.AppPhotoSelector
import com.autistasgourmet.pethub.ui.components.AppSectionText
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton
import com.autistasgourmet.pethub.ui.components.appChips.AppChoiceChips
import com.autistasgourmet.pethub.ui.components.appChips.AppFilterChips
import com.autistasgourmet.pethub.ui.components.appFields.AppNumberField
import com.autistasgourmet.pethub.ui.components.appFields.AppTextArea
import com.autistasgourmet.pethub.ui.components.appFields.AppTextField

@Composable
fun RegisterPetScreen(
    viewModel: RegisterPetViewModel = hiltViewModel(),
    onRegisterPetSuccess: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.saveSuccess.collect { success ->
            if (success) {
                onRegisterPetSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Publicar Mascota en Adopción",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        AppSectionText(
            modifier = Modifier.padding(16.dp),
            title = "Fotos de la Mascota",
            textStyle = MaterialTheme.typography.titleMedium,
            content = {
                Spacer(modifier = Modifier.height(16.dp))
                AppPhotoSelector(
                    selectedPhotos = viewModel.selectedPhotoUris,
                    onPhotosSelected = viewModel::onPhotosSelected
                )
            }
        )

        BasicInfoSection(
            name = viewModel.name,
            nameError = viewModel.nameError,
            species = viewModel.species,
            size = viewModel.size,
            ageRange = viewModel.ageRange,
            gender = viewModel.gender,
            description = viewModel.description,
            descriptionError = viewModel.descriptionError,
            onNameChange = viewModel::onNameChange,
            onSpeciesChange = viewModel::onSpeciesChange,
            onSizeChange = viewModel::onSizeChange,
            onAgeRangeChange = viewModel::onAgeRangeChange,
            onGenderChange = viewModel::onGenderChange,
            onDescriptionChange = viewModel::onDescriptionChange
        )

        HealthInfoSection(
            isVaccinated = viewModel.isVaccinated,
            isSterilized = viewModel.isSterilized,
            isDewormed = viewModel.isDewormed,
            specialConditions = viewModel.specialConditions,
            onVaccinatedChange = viewModel::onVaccinatedChange,
            onSterilizedChange = viewModel::onSterilizedChange,
            onDewormedChange = viewModel::onDewormedChange,
            onSpecialConditionsChange = viewModel::onSpecialConditionsChange
        )

        EnergySociabilitySection(
            energyLevel = viewModel.energyLevel,
            sociabilityKids = viewModel.sociabilityKids,
            sociabilityDogs = viewModel.sociabilityDogs,
            sociabilityCats = viewModel.sociabilityCats,
            onEnergyLevelChange = viewModel::onEnergyLevelChange,
            onSociabilityKidsChange = viewModel::onSociabilityKidsChange,
            onSociabilityDogsChange = viewModel::onSociabilityDogsChange,
            onSociabilityCatsChange = viewModel::onSociabilityCatsChange
        )

        TraitsSection(
            selectedTraits = viewModel.selectedTraits,
            onTraitToggled = viewModel::toggleTrait
        )

        LocationSection(
            postalCode = viewModel.postalCode,
            postalCodeError = viewModel.postalCodeError,
            onPostalCodeChange = viewModel::onPostalCodeChange
        )

        AppPrimaryButton(
            modifier = Modifier.padding(16.dp),
            text = if (viewModel.isLoading) "Publicando..." else "Publicar Mascota",
            onClick = viewModel::savePet,
            enabled = !viewModel.isLoading
        )
    }
}

@Composable
fun BasicInfoSection(
    name: String,
    nameError: String?,
    species: PetSpecies,
    size: PetSize,
    ageRange: PetAgeRange,
    gender: PetGender,
    description: String,
    descriptionError: String?,
    onNameChange: (String) -> Unit,
    onSpeciesChange: (PetSpecies) -> Unit,
    onSizeChange: (PetSize) -> Unit,
    onAgeRangeChange: (PetAgeRange) -> Unit,
    onGenderChange: (PetGender) -> Unit,
    onDescriptionChange: (String) -> Unit
) {
    AppSectionText(
        modifier = Modifier.padding(16.dp),
        title = "Información Básica",
        textStyle = MaterialTheme.typography.titleMedium,
        content = {
            Spacer(modifier = Modifier.height(16.dp))
            AppTextField(
                value = name,
                onValueChange = onNameChange,
                label = "NOMBRE",
                placeholder = "Ej. Max",
                isError = nameError != null,
                errorMessage = nameError
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                AppDropdown(
                    modifier = Modifier.weight(1f),
                    label = "ESPECIE",
                    options = PetSpecies.entries.map { it.displayName },
                    selectedOption = species.displayName,
                    onOptionSelected = { selected ->
                        onSpeciesChange(PetSpecies.entries.first { it.displayName == selected })
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppDropdown(
                    modifier = Modifier.weight(1f),
                    label = "TAMAÑO",
                    options = PetSize.entries.map { it.displayName },
                    selectedOption = size.displayName,
                    onOptionSelected = { selected ->
                        onSizeChange(PetSize.entries.first { it.displayName == selected })
                    }
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                AppDropdown(
                    modifier = Modifier.weight(1f),
                    label = "EDAD",
                    options = PetAgeRange.entries.map { it.displayName },
                    selectedOption = ageRange.displayName,
                    onOptionSelected = { selected ->
                        onAgeRangeChange(PetAgeRange.entries.first { it.displayName == selected })
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppDropdown(
                    modifier = Modifier.weight(1f),
                    label = "SEXO",
                    options = PetGender.entries.map { it.displayName },
                    selectedOption = gender.displayName,
                    onOptionSelected = { selected ->
                        onGenderChange(PetGender.entries.first { it.displayName == selected })
                    }
                )
            }

            AppTextArea(
                value = description,
                onValueChange = onDescriptionChange,
                label = "DESCRIPCIÓN",
                placeholder = "Cuenta un poco sobre su historia y por qué es especial...",
                isError = descriptionError != null,
                errorMessage = descriptionError
            )
        }
    )
}

@Composable
fun HealthInfoSection(
    isVaccinated: Boolean,
    isSterilized: Boolean,
    isDewormed: Boolean,
    specialConditions: String,
    onVaccinatedChange: (Boolean) -> Unit,
    onSterilizedChange: (Boolean) -> Unit,
    onDewormedChange: (Boolean) -> Unit,
    onSpecialConditionsChange: (String) -> Unit
) {
    AppSectionText(
        modifier = Modifier.padding(16.dp),
        title = "Información de Salud",
        textStyle = MaterialTheme.typography.titleMedium,
        content = {
            Spacer(modifier = Modifier.height(16.dp))
            AppCheckBox(label = "VACUNADO", checked = isVaccinated, onCheckedChange = onVaccinatedChange)
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            AppCheckBox(label = "ESTERILIZADO", checked = isSterilized, onCheckedChange = onSterilizedChange)
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            AppCheckBox(label = "DESPARASITADO", checked = isDewormed, onCheckedChange = onDewormedChange)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            AppTextArea(
                value = specialConditions,
                onValueChange = onSpecialConditionsChange,
                label = "CONDICIONES ESPECIALES",
                placeholder = "Ninguna"
            )
        }
    )
}

@Composable
fun EnergySociabilitySection(
    energyLevel: EnergyLevel,
    sociabilityKids: SociabilityLevel,
    sociabilityDogs: SociabilityLevel,
    sociabilityCats: SociabilityLevel,
    onEnergyLevelChange: (EnergyLevel) -> Unit,
    onSociabilityKidsChange: (SociabilityLevel) -> Unit,
    onSociabilityDogsChange: (SociabilityLevel) -> Unit,
    onSociabilityCatsChange: (SociabilityLevel) -> Unit
) {
    AppSectionText(
        modifier = Modifier.padding(16.dp),
        title = "Energía y Sociabilidad",
        textStyle = MaterialTheme.typography.titleMedium,
        content = {
            Spacer(modifier = Modifier.height(16.dp))
            AppChoiceChips(
                label = "NIVEL DE ENERGÍA",
                options = EnergyLevel.entries.map { it.displayName },
                selectedOption = energyLevel.displayName,
                onOptionSelected = { selected ->
                    onEnergyLevelChange(EnergyLevel.entries.first { it.displayName == selected })
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "NIVEL DE SOCIABILIDAD", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            SociabilityRow(
                label = "CON NIÑOS",
                currentLevel = sociabilityKids,
                onLevelChange = onSociabilityKidsChange
            )
            SociabilityRow(
                label = "CON PERROS",
                currentLevel = sociabilityDogs,
                onLevelChange = onSociabilityDogsChange
            )
            SociabilityRow(
                label = "CON GATOS",
                currentLevel = sociabilityCats,
                onLevelChange = onSociabilityCatsChange
            )
        }
    )
}

@Composable
fun SociabilityRow(
    label: String,
    currentLevel: SociabilityLevel,
    onLevelChange: (SociabilityLevel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        AppDropdown(
            modifier = Modifier.width(180.dp),
            label = "",
            options = SociabilityLevel.entries.map { it.displayName },
            selectedOption = currentLevel.displayName,
            onOptionSelected = { selected ->
                onLevelChange(SociabilityLevel.entries.first { it.displayName == selected })
            }
        )
    }
}

@Composable
fun TraitsSection(
    selectedTraits: List<PetTrait>,
    onTraitToggled: (PetTrait) -> Unit
) {
    AppSectionText(
        modifier = Modifier.padding(16.dp),
        title = "Rasgos de Personalidad",
        textStyle = MaterialTheme.typography.titleMedium,
        content = {
            Spacer(modifier = Modifier.height(16.dp))
            AppFilterChips(
                label = "Selecciona sus rasgos",
                options = PetTrait.entries.map { it.displayName },
                selectedOptions = selectedTraits.map { it.displayName },
                onOptionToggled = { displayName ->
                    val trait = PetTrait.entries.first { it.displayName == displayName }
                    onTraitToggled(trait)
                }
            )
        }
    )
}

@Composable
fun LocationSection(
    postalCode: String,
    postalCodeError: String?,
    onPostalCodeChange: (String) -> Unit
) {
    AppSectionText(
        modifier = Modifier.padding(16.dp),
        title = "Ubicación",
        textStyle = MaterialTheme.typography.titleMedium,
        content = {
            Spacer(modifier = Modifier.height(16.dp))
            AppHighlightedText(
                normalText = "¿Dónde se encuentra la mascota? Ingresa el ",
                highlightedText = "Código Postal."
            )
            AppNumberField(
                value = postalCode,
                onValueChange = onPostalCodeChange,
                label = "Código Postal",
                placeholder = "Ej. 06000",
                isError = postalCodeError != null,
                errorMessage = postalCodeError
            )
        }
    )
}
