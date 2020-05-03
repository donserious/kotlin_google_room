package com.example.kotlin_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = arrayOf(Command::class), version = 1, exportSchema = false)
public abstract class CommandRoomDatabase : RoomDatabase() {

    abstract fun cmdDao(): CommandDAO


    private class CmdDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var cmdDao = database.cmdDao()

                    // Delete all content here.
                    cmdDao.deleteAll()

                    // Add sample words.
                    var cmd = Command("main","netstat -tupan","отображение портов", UUID.randomUUID().toString()+"0000000")
                    cmdDao.insert(cmd)
                    cmd = Command("main","iotop","операции чтения и записи",UUID.randomUUID().toString()+"0000000")
                    cmdDao.insert(cmd)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CommandRoomDatabase? = null


        fun getDatabase(context: Context, scope: CoroutineScope): CommandRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommandRoomDatabase::class.java,
                    "cmd_database"
                )
                    .addCallback(CmdDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}