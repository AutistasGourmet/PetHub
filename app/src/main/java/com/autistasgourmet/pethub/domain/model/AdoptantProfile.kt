package com.autistasgourmet.pethub.domain.model

data class AdoptantProfile(
    val userId: String,
    val bio: String,
    val location: String,
    val phoneNumber: String,
    val favoritePets: List<String> = emptyList()
)
