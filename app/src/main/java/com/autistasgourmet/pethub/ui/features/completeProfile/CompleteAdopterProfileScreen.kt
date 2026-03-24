package com.autistasgourmet.pethub.ui.features.completeProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autistasgourmet.pethub.domain.model.AdopterExperience
import com.autistasgourmet.pethub.domain.model.HousingType
import com.autistasgourmet.pethub.ui.components.AppCheckBox
import com.autistasgourmet.pethub.ui.components.AppDropdown
import com.autistasgourmet.pethub.ui.components.AppSectionText
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton
import com.autistasgourmet.pethub.ui.components.appFields.AppNumberField
import com.autistasgourmet.pethub.ui.components.appFields.AppTextArea
import com.autistasgourmet.pethub.ui.components.appFields.AppTextField
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@Composable
fun CompleteAdopterProfileScreen(
    onBack: () -> Unit,
    viewModel: CompleteAdopterProfileViewModel
) {
    //field states
    val name = viewModel.name
    val lastName = viewModel.lastName
    val age = viewModel.age
    val occupation = viewModel.occupation
    val postalCode = viewModel.postalCode
    val housingType = viewModel.housingType
    val hasPatio = viewModel.hasPatio
    val spaceRoutineDetails = viewModel.spaceRoutineDetails
    val petExperience = viewModel.petExperience
    val hasDogs = viewModel.hasDogs
    val hasCats = viewModel.hasCats
    val hasKids = viewModel.hasKids

    //care details compromises
    val vetVisits = viewModel.vetVisits
    val vaccination = viewModel.vaccination
    val deworming = viewModel.deworming
    val properHygiene = viewModel.properHygiene
    val cleanWater = viewModel.cleanWater
    val kibbleFeeding = viewModel.kibbleFeeding

    //ui states
    val isLoading = viewModel.isLoading
    val nameError = viewModel.nameError
    val lastNameError = viewModel.lastNameError
    val ageError = viewModel.ageError
    val occupationError = viewModel.occupationError
    val postalCodeError = viewModel.postalCodeError
    val spaceDetailsError = viewModel.spaceDetailsError



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PetHubTheme {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                AppSectionText(
                    title = "Información Básica",
                    content = {
                        Spacer(modifier = Modifier.height(16.dp))
                        AppTextField(
                            value = name,
                            onValueChange = viewModel::onNameChange,
                            label = "Nombre",
                            placeholder = "ingresa tu nombre",
                            isError = nameError != null,
                            errorMessage = nameError
                        )
                        AppTextField(
                            value = lastName,
                            onValueChange = viewModel::onLastNameChange,
                            label = "Apellidos",
                            placeholder = "ingresa tus apellidos",
                            isError = lastNameError != null,
                            errorMessage = lastNameError
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            AppTextField(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                value = occupation,
                                onValueChange = viewModel::onOccupationChange,
                                label = "Ocupación",
                                placeholder = "tu ocupación",
                                isError = occupationError != null,
                                errorMessage = occupationError
                            )
                            Spacer(modifier = Modifier.padding(15.dp))

                            AppNumberField(
                                value = age,
                                onValueChange = viewModel::onAgeChange,
                                label = "Edad",
                                placeholder = "tu edad",
                                isError = ageError != null,
                                errorMessage = ageError
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        //AppPrimaryButton(text = "algun boton", onClick = {})
                    },
                    textColor = MaterialTheme.colorScheme.primary
                )
            }
        }

        PetHubTheme {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                AppSectionText(
                    title = "Cuestionario de adoptante",
                    content = {
                        Spacer(modifier = Modifier.height(16.dp))
                        var selectedHouseType by remember { mutableStateOf(HousingType.APARTAMENTO) }
                        AppDropdown(
                            label = "Tipo de vivienda",
                            options = HousingType.entries.map { it.displayName },
                            selectedOption = selectedHouseType.displayName,
                            onOptionSelected = { selectedString ->
                                selectedHouseType = HousingType.entries.first { it.displayName == selectedString }
                            }
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        AppCheckBox(
                            label = "La vivienda cuenta con patio, terraza o jardin seguro",
                            checked = hasPatio, //desde el viewmodel viene en FALSE
                            onCheckedChange = viewModel::onHasPatioChange,
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        var selectedAdoptExperience by remember { mutableStateOf(AdopterExperience.PRIMERA_VEZ) }
                        AppDropdown(
                            label = "Experiencia con mascotas",
                            options = AdopterExperience.entries.map { it.displayName },
                            selectedOption = selectedAdoptExperience.displayName,
                            onOptionSelected = { selectedString ->
                                selectedAdoptExperience = AdopterExperience.entries.first { it.displayName == selectedString }
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
                            checked = hasDogs, //desde el viewmodel viene en FALSE
                            onCheckedChange = viewModel::onHasDogsChange,
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        AppCheckBox(
                            label = "GATOS",
                            checked = hasCats, //desde el viewmodel viene en FALSE
                            onCheckedChange = viewModel::onHasCatsChange,
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        AppCheckBox(
                            label = "NIÑOS",
                            checked = hasKids, //desde el viewmodel viene en FALSE
                            onCheckedChange = viewModel::onHasKidsChange,
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        AppTextArea(
                            value = spaceRoutineDetails,
                            onValueChange = viewModel::onSpaceRoutineDetailsChange,
                            label = "Detalles sobre espacio y rutina",
                            placeholder = "Describe los espacios que puedes brindar y tu rutina diaria"
                        )
                    },
                    textColor = MaterialTheme.colorScheme.primary
                )
            }
        }

        PetHubTheme{
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                AppSectionText(
                    title = "Cuidados que estás dispuesto a dar:",
                    content = {
                        AppCheckBox(
                            label = "Visitas al Veterinario",
                            checked = vetVisits,//desde el viewmodel viene en FALSE
                            onCheckedChange = viewModel::onVetVisitsChange
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        AppCheckBox(
                            label = "Vacunación",
                            checked = vaccination,//desde el viewmodel viene en FALSE
                            onCheckedChange = viewModel::onVaccinationChange
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        AppCheckBox(
                            label = "Desparacitación",
                            checked = deworming,//desde el viewmodel viene en FALSE
                            onCheckedChange = viewModel::onDewormingChange
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                )
            }
        }
        
        Button(onClick = onBack) {
            Text("Regresar")
        }
    }
}
