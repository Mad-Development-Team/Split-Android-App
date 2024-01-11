package com.madteam.split.data.mapper.user

import com.madteam.split.data.model.response.UserDTO
import com.madteam.split.domain.model.User

fun UserDTO.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        profileImage = profileImage,
        createdDate = createdDate,
    )
}