package com.example.notesappfull

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesappfull.RomDB.Note
import com.example.notesappfull.RomDB.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Model (app: Application): AndroidViewModel(app) {

    private val notes: LiveData<List<Note>>

    val cor = NoteDatabase.getDatabase(app)

    init {

        notes=cor.noteDao().getNotes()
    }

    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun addNote(noteText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            cor.noteDao().addNote(Note(0, noteText))
        }
    }

    fun editNote(noteID: Int, noteText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            cor.noteDao().updateNote(Note(noteID, noteText))
        }
    }

    fun deleteNote(noteID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            cor.noteDao().deleteNote(Note(noteID, ""))
        }
    }
}

