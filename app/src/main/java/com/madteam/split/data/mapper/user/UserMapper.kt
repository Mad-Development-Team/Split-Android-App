package com.madteam.split.data.mapper.user

import com.madteam.split.data.database.user.entities.UserEntity
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

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        profileImage = profileImage,
        createdDate = createdDate,
    )
}

fun UserEntity.toUser(): User {
    return User(
      id = id,
      name = name,
      email = email,
      profileImage = profileImage,
      createdDate = createdDate,
    )
}

fun UserDTO.toEntity(): UserEntity {
    return UserEntity(
      id = id,
      name = name,
      email = email,
      profileImage = profileImage,
      createdDate = createdDate,
    )
}

fun User.toDTO(): UserDTO {
    return UserDTO(
      id = id,
      name = name,
      email = email,
      profileImage = profileImage,
      createdDate = createdDate,
    )
}