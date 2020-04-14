package com.example.kotlin_room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommandDAO {
    @Query("SELECT * from cmd_table ORDER BY cmd ASC")
    fun getAlphabetizedCmd(): LiveData<List<Command>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cmd: Command)

    @Query("DELETE FROM cmd_table")
    suspend fun deleteAll()
}