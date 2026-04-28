package com.autistasgourmet.pethub.ui.components.appChips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autistasgourmet.pethub.ui.theme.PetHubTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppFilterChips(
    label: String,
    options: List<String>,
    selectedOptions: List<String>,
    onOptionToggled: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedOptions.contains(option)

                Surface(
                    onClick = { onOptionToggled(option) },
                    modifier = Modifier
                        .height(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color =
                        if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        else MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // +  ✓
                        Text(
                            text = if (isSelected) "✓ " else "+ ",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppFilterChipsPreview() {
    PetHubTheme {
        var selectedOptions by remember { mutableStateOf(listOf("Timido")) }
        val options = listOf(
            "Jutgon", "Timido", "Indeoenduiente", "Guarian",
            "faldero", "cariñoso", "tranquilo", "activo"
        )

        AppFilterChips(
            label = "Rasgos de su personalidad",
            options = options,
            selectedOptions = selectedOptions,
            onOptionToggled = { option ->
                selectedOptions = if (selectedOptions.contains(option)) {
                    selectedOptions - option
                } else {
                    selectedOptions + option
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
