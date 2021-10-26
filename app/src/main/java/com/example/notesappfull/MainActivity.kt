package com.example.notesappfull

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var rcv : RecyclerView
    lateinit var edNewNote: EditText
    lateinit var btnSubmit: Button
    private lateinit var dbhelp : DBhelper


    private val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }
    private val repository by lazy { NoteRepo(noteDao) }


    private lateinit var notes : List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edNewNote = findViewById(R.id.edNewNote)
        btnSubmit = findViewById(R.id.btnSubmit)
        dbhelp = DBhelper(this)
        rcv = findViewById(R.id.rcv)

        notes = listOf()

        btnSubmit.setOnClickListener {
            addNote(edNewNote.text.toString())
            edNewNote.text.clear()
            edNewNote.clearFocus()
            updateRV()
        }
        getItemsList()
        updateRV()
    }

    fun updateRV(){
        rcv.adapter = RVadaptar(this,notes)
        rcv.layoutManager = LinearLayoutManager(this)

    }

    private fun getItemsList(){
        CoroutineScope(Dispatchers.IO).launch {
            val data = async {
                repository.getNotes
            }.await()
            if(data.isNotEmpty()){
                notes = data
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data", )
            }
        }
    }

    private fun addNote(noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addNote(Note(0, noteText))
        }
    }

    private fun editNote(noteID: Int, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateNote(Note(noteID,noteText))
        }
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteNote(Note(noteID,""))
        }
    }

    fun raiseDialog(id: Int){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {

                    _, _ ->
                run {
                    editNote(id, updatedNote.text.toString())
                    updateRV()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}