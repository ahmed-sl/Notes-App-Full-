package com.example.notesappfull

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Model (app: Application): AndroidViewModel(app) {
    private val cor: NoteRepo
    private val notes: LiveData<List<Note>>

    init {
        val notedao = NoteDatabase.getDatabase(app).noteDao()
        cor = NoteRepo(notedao)
        notes = cor.getNotes
    }

    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun addNote(noteText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            cor.addNote(Note(0, noteText))
        }
    }

    fun editNote(noteID: Int, noteText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            cor.updateNote(Note(noteID, noteText))
        }
    }

    fun deleteNote(noteID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            cor.deleteNote(Note(noteID, ""))
        }
    }
}

