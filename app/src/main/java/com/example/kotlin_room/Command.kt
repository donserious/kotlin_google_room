package com.example.kotlin_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cmd_table")
class Command (
    @ColumnInfo(name = "parent_cmd_id") val parent_id: String,
    @ColumnInfo(name = "cmd") val cmd: String,
    @ColumnInfo(name = "description_cmd") val description: String,
    @ColumnInfo(name = "unique_id") val uid: String)
{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}