package com.autistasgourmet.pethub.ui.features.adopt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdoptPetScreen(
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pantalla de Adopción (Tarjetas)", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = { onNavigateToDetail("id_de_prueba") }) {
            Text("Ver Detalle de Mascota")
        }
    }
}
