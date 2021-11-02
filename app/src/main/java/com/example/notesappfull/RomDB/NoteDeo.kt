package com.example.notesappfull

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesappfull.RomDB.Note

@Dao
interface NoteDao {

    //method to get all data:
    @Query("SELECT * FROM NotesTable")
    fun getAll(): List<Note>

    //insert a row into the table
    @Insert
    fun insertNote(note:Note)

    //update note
    @Update
    fun updateNote(note: Note)

    //delete note row
    @Delete
    fun delNote(noteid: Note)

}