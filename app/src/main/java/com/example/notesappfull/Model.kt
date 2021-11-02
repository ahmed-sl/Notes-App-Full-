package com.example.notesappfull

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesappfull.RomDB.Note
import com.example.notesappfull.RomDB.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Model : ViewModel(){

    private val id = MutableLiveData<Any>()
    val getId: LiveData<Any> get() = id

    fun setId(noteId: Int){
        id.value=noteId
    }

}

