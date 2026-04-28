package com.autistasgourmet.pethub.ui.components.appChips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement

enum class AppTraitChipType {
    SPECIES,
    SIZE,
    GENDER
}

@Composable
fun AppTraitChip(
    text: String,
    type: AppTraitChipType,
    modifier: Modifier = Modifier
) {
    val (borderColor, backgroundColor, textColor) = when(type) {
        AppTraitChipType.SPECIES -> Triple(
            Color(0xFF3B82F6), // Blue-500
            Color(0xFFEFF6FF), // Blue-50
            Color(0xFF1D4ED8)  // Blue-700
        )
        AppTraitChipType.SIZE -> Triple(
            Color(0xFFA855F7), // Purple-500
            Color(0xFFFAF5FF), // Purple-50
            Color(0xFF7E22CE)  // Purple-700
        )
        AppTraitChipType.GENDER -> Triple(
            Color(0xFF22C55E), // Green-500
            Color(0xFFF0FDF4), // Green-50
            Color(0xFF15803D)  // Green-700
        )
    }

    Surface(
        modifier = modifier.padding(end = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(percent = 50),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTraitChipPreview() {
    MaterialTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppTraitChip(text = "Perro", type = AppTraitChipType.SPECIES)
            AppTraitChip(text = "Mediano", type = AppTraitChipType.SIZE)
            AppTraitChip(text = "Macho", type = AppTraitChipType.GENDER)
        }
    }
}
