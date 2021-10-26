package com.example.notesappfull

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    lateinit var rcv : RecyclerView
    lateinit var edNewNote: EditText
    lateinit var btnSubmit: Button
    private lateinit var dbhelp : DBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edNewNote = findViewById(R.id.edNewNote)
        btnSubmit = findViewById(R.id.btnSubmit)
        dbhelp = DBhelper(this)
        rcv = findViewById(R.id.rcv)

        btnSubmit.setOnClickListener {
            if (edNewNote.text.isNotEmpty()){
                dbhelp.addData(edNewNote.text.toString())
                Toast.makeText(applicationContext, "Data is Add !", Toast.LENGTH_SHORT).show()
                updateRV(dbhelp.getData())
            }else{
                Toast.makeText(applicationContext, "Something Error !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun update(notes: NoteModel){
        dbhelp.updateData(notes)
        updateRV(dbhelp.getData())

    }
    fun delete(notes: NoteModel){
        dbhelp.deleteData(notes)
        updateRV(dbhelp.getData())

    }
    fun updateRV(lis : ArrayList<NoteModel>){
        rcv.adapter = RVadaptar(this,lis)
        rcv.layoutManager = LinearLayoutManager(this)

    }

    fun confirm(notes: NoteModel) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Confirm Delete")
        alert.setPositiveButton("Confirm") { _, _ ->
            delete(notes)
        }
        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        alert.show()
    }

    fun showDialog(notes: NoteModel) {
        val alert = AlertDialog.Builder(this)
        val editText = EditText(this)
        editText.setText(notes.noteText)
        alert.setTitle("Edit Note Title")
        alert.setView(editText)
        alert.setPositiveButton("Confirm") { _, _ ->
            update(NoteModel(notes.id, editText.text.toString()))
        }
        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        alert.show()
    }
}