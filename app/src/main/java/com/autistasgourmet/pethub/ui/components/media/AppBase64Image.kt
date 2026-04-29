package com.autistasgourmet.pethub.ui.components.media

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.autistasgourmet.pethub.domain.repository.PetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AppBase64Image(
    photoId: String,
    petRepository: PetRepository,
    modifier: Modifier = Modifier
) {
    var bitmap by remember(photoId) { mutableStateOf<android.graphics.Bitmap?>(null) }
    var isLoading by remember(photoId) { mutableStateOf(true) }
    var error by remember(photoId) { mutableStateOf(false) }

    LaunchedEffect(photoId) {
        isLoading = true
        error = false
        val result = petRepository.getPetPhoto(photoId)
        result.onSuccess { base64 ->
            if (base64.isNotEmpty()) {
                withContext(Dispatchers.Default) {
                    try {
                        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
                        bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    } catch (e: Exception) {
                        error = true
                    }
                }
            } else {
                error = true
            }
        }.onFailure {
            error = true
        }
        isLoading = false
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
            error || bitmap == null -> {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color(0xFFE5E7EB)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color.Gray.copy(alpha = 0.5f)
                    )
                }
            }
            else -> {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
