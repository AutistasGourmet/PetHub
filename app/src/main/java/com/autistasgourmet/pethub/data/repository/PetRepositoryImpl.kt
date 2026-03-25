package com.autistasgourmet.pethub.data.repository

import com.autistasgourmet.pethub.data.mapper.toDomain
import com.autistasgourmet.pethub.data.mapper.toDto
import com.autistasgourmet.pethub.data.remote.dto.PetDto
import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.repository.PetRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PetRepositoryImpl(
    private val firestore: FirebaseFirestore
) : PetRepository {

    private val petsCollection = firestore.collection("pets")
    private val photosCollection = firestore.collection("pet_photos")

    override fun getPets(): Flow<List<Pet>> = callbackFlow {
        val listener = petsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) { close(error); return@addSnapshotListener }
            val pets = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(PetDto::class.java)?.copy(id = doc.id)?.toDomain()
            } ?: emptyList()
            trySend(pets)
        }
        awaitClose { listener.remove() }
    }

    override fun getPetsByOwner(ownerEmail: String): Flow<List<Pet>> = callbackFlow {
        val listener = petsCollection
            .whereEqualTo("ownerEmail", ownerEmail)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val pets = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(PetDto::class.java)?.copy(id = doc.id)?.toDomain()
                } ?: emptyList()
                trySend(pets)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun getPetById(id: String): Pet? {
        return try {
            val doc = petsCollection.document(id).get().await()
            doc.toObject(PetDto::class.java)?.copy(id = doc.id)?.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun savePet(pet: Pet): Result<Unit> {
        return try {
            val dto = pet.toDto()
            if (pet.id.isBlank()) {
                petsCollection.add(dto).await()
            } else {
                petsCollection.document(pet.id).set(dto).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePet(id: String): Result<Unit> {
        return try {
            petsCollection.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun savePetPhoto(base64: String): Result<String> {
        return try {
            val photoData = mapOf("base64" to base64)
            val docRef = photosCollection.add(photoData).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPetPhoto(photoId: String): Result<String> {
        return try {
            val doc = photosCollection.document(photoId).get().await()
            val base64 = doc.getString("base64") ?: ""
            Result.success(base64)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
