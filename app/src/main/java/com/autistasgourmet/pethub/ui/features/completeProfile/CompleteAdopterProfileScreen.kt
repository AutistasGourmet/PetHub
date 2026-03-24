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
import com.autistasgourmet.pethub.ui.components.AppSectionText
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton
import com.autistasgourmet.pethub.ui.components.appFields.AppNumberField
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
                            value = "ingresa tu nombre",
                            onValueChange = {},
                            label = "Nombre"
                        )
                        AppTextField(
                            value = "ingresa tus apellidos",
                            onValueChange = {},
                            label = "Apellidos"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            //horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AppTextField(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                value = "tu ocupación",
                                onValueChange = {},
                                label = "Ocupación"
                            )
                            Spacer(modifier = Modifier.padding(15.dp))
                            var age by remember { mutableStateOf("") }

                            AppNumberField(
                                //modifier = Modifier.fillMaxWidth(0.3f),
                                value = age,
                                onValueChange = { age = it },
                                label = "Edad",
                                placeholder = "tu edad"
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
                        AppTextField(
                            value = "ingresa tu nombre",
                            onValueChange = {},
                            label = "Tipo de vivienda"
                        )
                        AppTextField(
                            value = "ingresa tus apellidos",
                            onValueChange = {},
                            label = "Apellidos"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            //horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AppTextField(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                value = "tu ocupación",
                                onValueChange = {},
                                label = "Ocupación"
                            )
                            Spacer(modifier = Modifier.padding(15.dp))
                            var age by remember { mutableStateOf("") }

                            AppNumberField(
                                //modifier = Modifier.fillMaxWidth(0.3f),
                                value = age,
                                onValueChange = { age = it },
                                label = "Edad",
                                placeholder = "tu edad"
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        //AppPrimaryButton(text = "algun boton", onClick = {})
                    },
                    textColor = MaterialTheme.colorScheme.primary
                )
            }
        }

//        Text(
//            text = "Completar Perfil de Adoptante",
//            style = MaterialTheme.typography.headlineSmall,
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text("Aquí irá el formulario de perfil...")
//
//        Spacer(modifier = Modifier.height(24.dp))
        
        Button(onClick = onBack) {
            Text("Regresar")
        }
    }
}
