package com.example.kotlin_room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommandViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CommandRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCmd: LiveData<List<Command>>

    init {
        val cmdsDao = CommandRoomDatabase.getDatabase(application,viewModelScope).cmdDao()
        repository = CommandRepository(cmdsDao)
        allCmd = repository.allCmd
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(cmd: Command) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cmd)
    }
}
