package com.example.notesappfull

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//class
data class NoteModel(val id: String, val noteText: String)

class DBhelper( context: Context
) : SQLiteOpenHelper(context, "NoteDB.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Notes(ID INTEGER PRIMARY KEY AUTOINCREMENT,Title Text Not null)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addData(s: String) {
        val c = ContentValues()
        c.put("Title", s)
        this.writableDatabase.insert("Notes", null, c)
    }

    fun deleteData(note:NoteModel){
        this.writableDatabase.delete("Notes", "ID=${note.id}", null)
    }

    fun updateData(note: NoteModel) {
        val cv = ContentValues()
        cv.put("ID", note.id)
        cv.put("Title", note.noteText)
        this.writableDatabase.update("Notes", cv, "ID=${note.id}", null)
    }

    @SuppressLint("Range")
    fun getData(): ArrayList<NoteModel> {
        val lis = arrayListOf<NoteModel>()
        val cursor = this.readableDatabase.query("Notes", null,
            null, null, null,
            null, null)

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val id = cursor.getString(cursor.getColumnIndex("ID"))
            val title = cursor.getString(cursor.getColumnIndex("Title"))
            lis.add(NoteModel(id, title))
            cursor.moveToNext()
        }

        return lis
    }
}
