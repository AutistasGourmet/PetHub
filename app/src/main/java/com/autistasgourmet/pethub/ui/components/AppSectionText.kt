package com.autistasgourmet.pethub.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.autistasgourmet.pethub.ui.components.appButtons.AppPrimaryButton
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@Composable
fun AppSectionText(
    modifier: Modifier = Modifier,
    title: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = modifier
                .padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppSectionTextWithContenPreview() {
    PetHubTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AppSectionText(
                title = "titulo de la seccion",
                content = {
                    Text(text = "contenido")
                    Spacer(modifier = Modifier.height(16.dp))
                    AppTextField(
                        value = "blabla",
                        onValueChange = {},
                        label = "campo de texto"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppPrimaryButton(text = "algun boton", onClick = {})
                },
                textColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppSectionTextPreview() {
    PetHubTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AppSectionText(
                title = "titulo de la seccion",
                content = {
                    Text(text = "contenido")
                }
            )
        }
    }
}