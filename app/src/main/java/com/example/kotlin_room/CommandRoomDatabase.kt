package com.example.kotlin_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Command::class), version = 1, exportSchema = false)
public abstract class CommandRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): CommandDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CommandRoomDatabase? = null

        fun getDatabase(context: Context): CommandRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommandRoomDatabase::class.java,
                    "cmd_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}