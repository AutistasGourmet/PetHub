package com.autistasgourmet.pethub.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun AppHighlightedText(
    modifier: Modifier = Modifier,
    normalText: String,
    highlightedText: String,
    normalTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    highlightedTextColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = normalTextColor
                )
            ) {
                append(normalText)
            }

            // Estilo para el texto resaltado
            withStyle(
                style = SpanStyle(
                    color = highlightedTextColor,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(highlightedText)
            }
        },
        style = MaterialTheme.typography.bodyMedium
    )
}