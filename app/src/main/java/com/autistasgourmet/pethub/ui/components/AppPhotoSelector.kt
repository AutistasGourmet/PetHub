package com.autistasgourmet.pethub.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AppPhotoSelector(
    selectedPhotos: List<Uri>,
    onPhotosSelected: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Slot 1: Principal
        PhotoSlot(
            uri = selectedPhotos.getOrNull(0),
            label = "Principal",
            icon = Icons.Default.PhotoCamera,
            onPhotoSelected = { uri ->
                val newList = selectedPhotos.toMutableList()
                if (newList.size > 0) newList[0] = uri else newList.add(uri)
                onPhotosSelected(newList)
            },
            modifier = Modifier.weight(1f)
        )

        // Slot 2: Agregar
        PhotoSlot(
            uri = selectedPhotos.getOrNull(1),
            label = "Agregar",
            icon = Icons.Default.Add,
            onPhotoSelected = { uri ->
                val newList = selectedPhotos.toMutableList()
                if (newList.size > 1) newList[1] = uri else newList.add(uri)
                onPhotosSelected(newList)
            },
            modifier = Modifier.weight(1f)
        )

        // Slot 3: Agregar
        PhotoSlot(
            uri = selectedPhotos.getOrNull(2),
            label = "Agregar",
            icon = Icons.Default.Add,
            onPhotoSelected = { uri ->
                val newList = selectedPhotos.toMutableList()
                if (newList.size > 2) newList[2] = uri else newList.add(uri)
                onPhotosSelected(newList)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PhotoSlot(
    uri: Uri?,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onPhotoSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { resultUri ->
        resultUri?.let { onPhotoSelected(it) }
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .then(
                if (uri == null) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            )
            .clickable { launcher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        if (uri != null) {
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }
        }
    }
}
