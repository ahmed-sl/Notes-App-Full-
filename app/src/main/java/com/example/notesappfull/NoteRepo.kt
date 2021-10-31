package com.example.notesappfull

import androidx.lifecycle.LiveData

class NoteRepo (private val noteDao: NoteDao) {

    val getNotes: LiveData<List<Note>> = noteDao.getNotes()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

}