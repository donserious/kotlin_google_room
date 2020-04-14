package com.example.kotlin_room

import androidx.lifecycle.LiveData

class CommandRepository (private val cmdDao: CommandDAO) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allCmd: LiveData<List<Command>> = cmdDao.getAlphabetizedCmd()

    suspend fun insert(cmd: Command) {
        cmdDao.insert(cmd)
    }
}