package com.autistasgourmet.pethub.domain.model

data class Pet(
    val id: String,
    val name: String,
    val breed: String,
    val age: Int,
    val description: String,
    val imageUrl: String
)
