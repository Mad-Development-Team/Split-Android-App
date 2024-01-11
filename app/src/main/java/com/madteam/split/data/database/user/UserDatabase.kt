package com.madteam.split.data.database.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madteam.split.data.database.user.dao.UserDAO
import com.madteam.split.data.database.user.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDAO
}