package com.autistasgourmet.pethub.domain.model

data class User(
    val uid: String,
    val email: String,
    val name: String,
    val profileImageUrl: String? = null
)
