package com.madteam.split.data.database.user.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_TABLE_NAME = "user_table"

@Entity(tableName = USER_TABLE_NAME)
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "profile_image") var profileImage: String,
    @ColumnInfo(name = "created_date") var createdDate: String,
) {
    constructor() : this(0, "", "", "", "")
}
