package com.autistasgourmet.pethub.ui.features.publish

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.autistasgourmet.pethub.ui.components.AppDropdown
import com.autistasgourmet.pethub.ui.components.AppSectionText
import com.autistasgourmet.pethub.ui.components.appFields.AppTextField

@Composable
fun PublishScreen(
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

        AppSectionText(
            title = "Información Básica",
            content = {
                BasicInfoContent()
            }
        )
    }
}

@Composable
fun BasicInfoContent(){
    var name by remember { mutableStateOf("") }
    AppTextField(
        value = name,
        onValueChange = {name = it},
        label = "Nombre *",
        placeholder = "Ej: Max",
        errorMessage = "Este campo es obligatorio"
    )

    var selectedOption by remember { mutableStateOf("Perro") }
    AppDropdown(
        label = "Tipo de Mascota *",
        options = listOf("Perro", "Gato", "Otro"),
        selectedOption = selectedOption,
        onOptionSelected = {selectedOption = it}
    )

    var bred by remember { mutableStateOf("") }
    AppTextField(
        value = bred,
        onValueChange = {bred = it},
        label = "Raza Predominante",
        placeholder = "Mestizo, Labrador"
    )
}