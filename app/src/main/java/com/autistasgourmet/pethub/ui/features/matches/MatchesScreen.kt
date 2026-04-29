package com.autistasgourmet.pethub.ui.features.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autistasgourmet.pethub.ui.components.appCards.MatchUserCard
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@Composable
fun MatchesScreen(
    viewModel: MatchesViewModel = hiltViewModel(),
    onNavigateToProfile: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    MatchesScreenContent(
        uiState = uiState,
        onNavigateToProfile = onNavigateToProfile
    )
}

@Composable
fun MatchesScreenContent(
    uiState: MatchesUiState,
    onNavigateToProfile: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Personas interesadas en tus mascotas",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.matches.isEmpty()) {
            // Estado vacío (Excepción E1)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no existen candidatos para tus publicaciones.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Indicador de interesados
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Interesados",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.matches.size.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(uiState.matches) { match ->
                    MatchUserCard(
                        userName = match.fullName,
                        interestedInPetName = match.interestedInPetName,
                        location = match.location,
                        description = match.description,
                        onViewProfileClick = { onNavigateToProfile(match.userId, match.interestedInPetName) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatchesScreenPreview() {
    PetHubTheme {
        MatchesScreenContent(
            uiState = MatchesUiState(
                isLoading = false,
                matches = listOf(
                    MatchItemUiState(
                        userId = "1",
                        fullName = "Ángel Segovia",
                        interestedInPetName = "Toby",
                        location = "Guadalupe, Zac.",
                        description = "Apartamento, Tengo Mascotas Actualmente"
                    ),
                    MatchItemUiState(
                        userId = "2",
                        fullName = "Luis Gutiérrez",
                        interestedInPetName = "Toby",
                        location = "Guadalupe, Zac.",
                        description = "Apartamento, Tengo Mascotas Actualmente"
                    )
                )
            ),
            onNavigateToProfile = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MatchesScreenEmptyPreview() {
    PetHubTheme {
        MatchesScreenContent(
            uiState = MatchesUiState(
                isLoading = false,
                matches = emptyList()
            ),
            onNavigateToProfile = { _, _ -> }
        )
    }
}
