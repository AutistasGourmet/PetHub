package com.autistasgourmet.pethub.data.remote.dto

data class PetDto(
    val id: String = "",
    val name: String = "",
    val breed: String = "",
    val age: Int = 0,
    val description: String = "",
    val imageUrl: String = ""
)
