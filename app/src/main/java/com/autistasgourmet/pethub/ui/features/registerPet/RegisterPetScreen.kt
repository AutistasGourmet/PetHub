package com.autistasgourmet.pethub.ui.features.registerPet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton

@Composable
fun RegisterPetScreen(
    onRegisterPetSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Publicar Mascota en Adopción",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Formulario de registro de mascota...")

        Spacer(modifier = Modifier.height(24.dp))

        AppPrimaryButton(
            text = "Publicar Mascota",
            onClick = onRegisterPetSuccess
        )
    }
}
