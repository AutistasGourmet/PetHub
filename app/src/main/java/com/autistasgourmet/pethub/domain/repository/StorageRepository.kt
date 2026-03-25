package com.autistasgourmet.pethub.domain.repository

import android.net.Uri

interface StorageRepository {
    suspend fun uploadPetPhoto(uri: Uri, petId: String): Result<String>
    suspend fun uploadProfilePhoto(uri: Uri, userId: String): Result<String>
}
