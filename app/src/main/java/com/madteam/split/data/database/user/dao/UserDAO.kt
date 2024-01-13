package com.madteam.split.data.database.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.madteam.split.data.database.user.entities.USER_TABLE_NAME
import com.madteam.split.data.database.user.entities.UserEntity

@Dao
interface UserDAO {

    @Query("SELECT * FROM $USER_TABLE_NAME")
    suspend fun getUserInfo(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userEntity: UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserInfo(userEntity: UserEntity)

    @Query("DELETE FROM $USER_TABLE_NAME")
    suspend fun deleteAllUserInfo()

    @Query("UPDATE $USER_TABLE_NAME SET profile_image = '' WHERE id = :userId")
    suspend fun clearProfileImage(userId: Int)
}