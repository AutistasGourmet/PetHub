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
import androidx.compose.material.icons.filled.Group
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
import com.autistasgourmet.pethub.ui.components.appCards.QuickAccessCard
@Composable
fun HomeScreen(
    onNavigateToAdopt: () -> Unit,
    onNavigateToPublish: () -> Unit,
    onNavigateToMatches: () -> Unit
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuickAccessCard(
                title = "Mis Matches",
                description = "Descubre quién está listo para adoptar",
                icon = Icons.Default.Group,
                iconContainerColor = Color.Green.copy(alpha = 0.1f),
                iconColor = Color.Green,
                modifier = Modifier.weight(1f),
                onClick = onNavigateToMatches
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
