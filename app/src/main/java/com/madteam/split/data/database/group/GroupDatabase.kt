package com.madteam.split.data.database.group

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madteam.split.data.database.group.dao.GroupDAO
import com.madteam.split.data.database.group.entities.GroupEntity

@Database(entities = [GroupEntity::class], version = 1)
abstract class GroupDatabase : RoomDatabase() {
    abstract fun getGroupDao(): GroupDAO
}