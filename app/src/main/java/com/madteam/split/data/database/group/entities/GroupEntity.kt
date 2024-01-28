package com.madteam.split.data.database.group.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.madteam.split.data.database.converters.Converters
import com.madteam.split.domain.model.Member

const val GROUP_TABLE_NAME = "group_table"

@TypeConverters(Converters::class)
@Entity(tableName = GROUP_TABLE_NAME)
data class GroupEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "created_date") var createdDate: String,
    @ColumnInfo(name = "invite_code") var inviteCode: String,
    @ColumnInfo(name = "members") var members: List<Member>,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "banner_image") var bannerImage: String?,
)
