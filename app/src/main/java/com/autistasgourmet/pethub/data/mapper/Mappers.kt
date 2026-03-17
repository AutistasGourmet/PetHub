package com.autistasgourmet.pethub.data.mapper

import com.autistasgourmet.pethub.data.remote.dto.PetDto
import com.autistasgourmet.pethub.data.remote.dto.UserDto
import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.model.User

fun PetDto.toDomain(): Pet {
    return Pet(
        id = id,
        name = name,
        breed = breed,
        age = age,
        description = description,
        imageUrl = imageUrl
    )
}

fun Pet.toDto(): PetDto {
    return PetDto(
        id = id,
        name = name,
        breed = breed,
        age = age,
        description = description,
        imageUrl = imageUrl
    )
}

fun UserDto.toDomain(): User {
    return User(
        uid = uid,
        email = email,
        name = name,
        profileImageUrl = profileImageUrl
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        uid = uid,
        email = email,
        name = name,
        profileImageUrl = profileImageUrl
    )
}
