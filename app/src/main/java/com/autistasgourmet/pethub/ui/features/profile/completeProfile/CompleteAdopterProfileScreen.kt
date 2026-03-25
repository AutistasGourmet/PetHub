package com.autistasgourmet.pethub.ui.features.profile.completeProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.autistasgourmet.pethub.domain.model.AdopterExperience
import com.autistasgourmet.pethub.domain.model.HousingType
import com.autistasgourmet.pethub.ui.components.AppCheckBox
import com.autistasgourmet.pethub.ui.components.AppDropdown
import com.autistasgourmet.pethub.ui.components.AppHighlightedText
import com.autistasgourmet.pethub.ui.components.AppSectionText
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton
import com.autistasgourmet.pethub.ui.components.appFields.AppNumberField
import com.autistasgourmet.pethub.ui.components.appFields.AppTextArea
import com.autistasgourmet.pethub.ui.components.appFields.AppTextField

@Composable
fun CompleteAdopterProfileScreen(
    viewModel: CompleteAdopterProfileViewModel,
    onSaveSuccess: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.saveSuccess.collect { success ->
            if (success) {
                onSaveSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicInfoSection(
            name = viewModel.name,
            lastName = viewModel.lastName,
            age = viewModel.age,
            occupation = viewModel.occupation,
            nameError = viewModel.nameError,
            lastNameError = viewModel.lastNameError,
            ageError = viewModel.ageError,
            occupationError = viewModel.occupationError,
            onNameChange = viewModel::onNameChange,
            onLastNameChange = viewModel::onLastNameChange,
            onAgeChange = viewModel::onAgeChange,
            onOccupationChange = viewModel::onOccupationChange
        )

        AdopterQuestionnaireSection(
            housingType = viewModel.housingType,
            hasPatio = viewModel.hasPatio,
            petExperience = viewModel.petExperience,
            hasDogs = viewModel.hasDogs,
            hasCats = viewModel.hasCats,
            hasKids = viewModel.hasKids,
            spaceRoutineDetails = viewModel.spaceRoutineDetails,
            spaceDetailsError = viewModel.spaceDetailsError,
            onHousingTypeChange = viewModel::onHousingTypeChange,
            onHasPatioChange = viewModel::onHasPatioChange,
            onPetExperienceChange = viewModel::onPetExperienceChange,
            onHasDogsChange = viewModel::onHasDogsChange,
            onHasCatsChange = viewModel::onHasCatsChange,
            onHasKidsChange = viewModel::onHasKidsChange,
            onSpaceRoutineDetailsChange = viewModel::onSpaceRoutineDetailsChange
        )

        CareCommitmentsSection(
            vetVisits = viewModel.vetVisits,
            vaccination = viewModel.vaccination,
            deworming = viewModel.deworming,
            onVetVisitsChange = viewModel::onVetVisitsChange,
            onVaccinationChange = viewModel::onVaccinationChange,
            onDewormingChange = viewModel::onDewormingChange
        )

        LocationSection(
            postalCode = viewModel.postalCode,
            postalCodeError = viewModel.postalCodeError,
            onPostalCodeChange = viewModel::onPostalCodeChange
        )

        AppPrimaryButton(
            modifier = Modifier.padding(16.dp),
            text = "Guardar Cambios",
            onClick = viewModel::saveProfile,
            enabled = !viewModel.isLoading
        )
    }
}

@Composable
fun BasicInfoSection(
    name: String,
    lastName: String,
    age: String,
    occupation: String,
    nameError: String?,
    lastNameError: String?,
    ageError: String?,
    occupationError: String?,
    onNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onOccupationChange: (String) -> Unit
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
                label = "Nombre",
                placeholder = "Ingresa tu nombre",
                isError = nameError != null,
                errorMessage = nameError
            )
            AppTextField(
                value = lastName,
                onValueChange = onLastNameChange,
                label = "Apellidos",
                placeholder = "Ingresa tus apellidos",
                isError = lastNameError != null,
                errorMessage = lastNameError
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AppTextField(
                    modifier = Modifier.weight(1f),
                    value = occupation,
                    onValueChange = onOccupationChange,
                    label = "Ocupación",
                    placeholder = "Tu ocupación",
                    isError = occupationError != null,
                    errorMessage = occupationError
                )
                Spacer(modifier = Modifier.padding(8.dp))
                AppNumberField(
                    modifier = Modifier.weight(1f),
                    value = age,
                    onValueChange = onAgeChange,
                    label = "Edad",
                    placeholder = "Tu edad",
                    isError = ageError != null,
                    errorMessage = ageError
                )
            }
        }
    )
}

@Composable
fun AdopterQuestionnaireSection(
    housingType: HousingType,
    hasPatio: Boolean,
    petExperience: AdopterExperience,
    hasDogs: Boolean,
    hasCats: Boolean,
    hasKids: Boolean,
    spaceRoutineDetails: String,
    spaceDetailsError: String?,
    onHousingTypeChange: (HousingType) -> Unit,
    onHasPatioChange: (Boolean) -> Unit,
    onPetExperienceChange: (AdopterExperience) -> Unit,
    onHasDogsChange: (Boolean) -> Unit,
    onHasCatsChange: (Boolean) -> Unit,
    onHasKidsChange: (Boolean) -> Unit,
    onSpaceRoutineDetailsChange: (String) -> Unit
) {
    AppSectionText(
        modifier = Modifier.padding(16.dp),
        title = "Cuestionario de adoptante",
        textStyle = MaterialTheme.typography.titleMedium,
        content = {
            Spacer(modifier = Modifier.height(16.dp))
            AppDropdown(
                label = "Tipo de vivienda",
                options = HousingType.entries.map { it.displayName },
                selectedOption = housingType.displayName,
                onOptionSelected = { selectedString ->
                    onHousingTypeChange(HousingType.entries.first { it.displayName == selectedString })
                }
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            AppCheckBox(
                label = "La vivienda cuenta con patio, terraza o jardin seguro",
                checked = hasPatio,
                onCheckedChange = onHasPatioChange,
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            AppDropdown(
                label = "Experiencia con mascotas",
                options = AdopterExperience.entries.map { it.displayName },
                selectedOption = petExperience.displayName,
                onOptionSelected = { selectedString ->
                    onPetExperienceChange(AdopterExperience.entries.first { it.displayName == selectedString })
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Actualmente tienes en casa:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            AppCheckBox(
                label = "PERROS",
                checked = hasDogs,
                onCheckedChange = onHasDogsChange,
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            AppCheckBox(
                label = "GATOS",
                checked = hasCats,
                onCheckedChange = onHasCatsChange,
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            AppCheckBox(
                label = "NIÑOS",
                checked = hasKids,
                onCheckedChange = onHasKidsChange,
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            AppTextArea(
                value = spaceRoutineDetails,
                onValueChange = onSpaceRoutineDetailsChange,
                label = "Detalles sobre espacio y rutina",
                placeholder = "Describe los espacios que puedes brindar y tu rutina diaria",
                isError = spaceDetailsError != null,
                errorMessage = spaceDetailsError
            )
        }
    )
}

@Composable
fun CareCommitmentsSection(
    vetVisits: Boolean,
    vaccination: Boolean,
    deworming: Boolean,
    onVetVisitsChange: (Boolean) -> Unit,
    onVaccinationChange: (Boolean) -> Unit,
    onDewormingChange: (Boolean) -> Unit
) {
    AppSectionText(
        modifier = Modifier.padding(16.dp),
        title = "Cuidados que estás dispuesto a dar:",
        textStyle = MaterialTheme.typography.titleMedium,
        content = {
            AppCheckBox(
                label = "Visitas al Veterinario",
                checked = vetVisits,
                onCheckedChange = onVetVisitsChange
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            AppCheckBox(
                label = "Vacunación",
                checked = vaccination,
                onCheckedChange = onVaccinationChange
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            AppCheckBox(
                label = "Desparacitación",
                checked = deworming,
                onCheckedChange = onDewormingChange
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
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
                normalText = "¿Dónde te encuentras? Ingresa el ",
                highlightedText = "Código Postal."
            )
            AppNumberField(
                value = postalCode,
                onValueChange = onPostalCodeChange,
                label = "Código Postal",
                placeholder = "Ej. 98064",
                isError = postalCodeError != null,
                errorMessage = postalCodeError
            )
        }
    )
}
