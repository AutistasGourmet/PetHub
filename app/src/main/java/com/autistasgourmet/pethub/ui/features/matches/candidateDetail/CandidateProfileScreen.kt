package com.autistasgourmet.pethub.ui.features.matches.candidateDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autistasgourmet.pethub.ui.components.appCards.CandidateDetailsCard
import com.autistasgourmet.pethub.ui.components.appCards.CandidateHeaderCard
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@Composable
fun CandidateProfileScreen(
    viewModel: CandidateProfileViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CandidateProfileScreenContent(
        uiState = uiState,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidateProfileScreenContent(
    uiState: CandidateProfileUiState,
    onBack: () -> Unit
) {
    // Manejo de Excepción E1: Retiro de interés repentino
    if (uiState.isError) {
        AlertDialog(
            onDismissRequest = onBack,
            title = { Text("Perfil no disponible") },
            text = { Text(uiState.errorMessage ?: "Ha ocurrido un error inesperado.") },
            confirmButton = {
                TextButton(onClick = onBack) {
                    Text("Aceptar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Perfil de Adoptante",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { // FA-1: Evaluación postergada
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.profile != null && !uiState.isError) {
                val profile = uiState.profile
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CandidateHeaderCard(
                        userName = profile.userName,
                        occupation = profile.occupation,
                        age = profile.age,
                        location = profile.location,
                        interestedInPetName = profile.interestedInPetName
                    )

                    CandidateDetailsCard(
                        housingType = profile.housingType,
                        patioDetails = profile.patioDetails,
                        kidsDetails = profile.kidsDetails,
                        experienceDetails = profile.experienceDetails,
                        otherPetsDogs = profile.otherPetsDogs,
                        otherPetsCats = profile.otherPetsCats,
                        routineDetails = profile.routineDetails,
                        careDetails = profile.careDetails
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CandidateProfileScreenPreview() {
    PetHubTheme {
        CandidateProfileScreenContent(
            uiState = CandidateProfileUiState(
                isLoading = false,
                profile = CandidateProfileData(
                    userId = "1",
                    userName = "Ángel Segovia",
                    occupation = "Licenciado en Fisioterapia",
                    age = "22 años",
                    location = "Guadalupe, Zac.",
                    interestedInPetName = "Toby",
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
            ),
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CandidateProfileScreenErrorPreview() {
    PetHubTheme {
        CandidateProfileScreenContent(
            uiState = CandidateProfileUiState(
                isLoading = false,
                isError = true,
                errorMessage = "El adoptante ha retirado su solicitud o eliminado su cuenta."
            ),
            onBack = {}
        )
    }
}
