package com.autistasgourmet.pethub.data.repository

import android.net.Uri
import com.autistasgourmet.pethub.domain.repository.StorageRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StorageRepositoryImpl(
    private val storage: FirebaseStorage
) : StorageRepository {

    override suspend fun uploadPetPhoto(uri: Uri, petId: String): Result<String> {
        return try {
            val fileName = UUID.randomUUID().toString()
            val ref = storage.reference.child("pets/$petId/$fileName")
            ref.putFile(uri).await()
            val url = ref.downloadUrl.await().toString()
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadProfilePhoto(uri: Uri, userId: String): Result<String> {
        return try {
            val ref = storage.reference.child("users/$userId/profile.jpg")
            ref.putFile(uri).await()
            val url = ref.downloadUrl.await().toString()
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
