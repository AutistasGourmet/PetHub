package com.autistasgourmet.pethub.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    iconColor: Color = Color(0xFF4B3D61),
    backgroundColor: Color = Color(0xFFF3F4F6)
) {
    // fondo
    Surface(
        modifier = modifier.size(size),
        shape = CircleShape,
        color = backgroundColor
    ) {
        val iconSize = size / 3

        Box(contentAlignment = Alignment.Center) {
            // pata izquierda
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .offset(x = -(size * 0.13f), y = -(size * 0.1f))
                    .rotate(-15f),
                tint = iconColor
            )
            // pata derecha
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .offset(x = (size * 0.13f), y = (size * 0.1f))
                    .rotate(15f),
                tint = iconColor
            )
        }
    }
}

@Composable
@Preview
fun AppLogoPreview() {
    AppLogo()
}