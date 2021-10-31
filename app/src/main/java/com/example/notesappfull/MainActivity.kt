package com.example.notesappfull

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
    lateinit var model: Model
    lateinit var RV: RVadaptar

    private lateinit var notes : List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model= ViewModelProvider(this).get(Model::class.java)
        model.getNotes().observe(this,{
                notes -> RV.update(notes)
        })

        edNewNote = findViewById(R.id.edNewNote)
        btnSubmit = findViewById(R.id.btnSubmit)
        rcv = findViewById(R.id.rcv)

        notes = listOf()

        btnSubmit.setOnClickListener {

            model.addNote(edNewNote.text.toString())
            edNewNote.text.clear()
            edNewNote.clearFocus()

        }


        rcv.adapter =  RVadaptar(this)
        rcv.layoutManager= LinearLayoutManager(this)
    }
    fun dilogfun(id:Int) {
        val build = AlertDialog.Builder(this)
        val update = EditText(this)
        update.hint = " enter new note for update "
        build.setCancelable(false)
            .setPositiveButton("save", DialogInterface.OnClickListener() { _, _ ->
                model.editNote(id, update.text.toString())
            })
            .setNegativeButton("deny", DialogInterface.OnClickListener {
                    dialog,_->dialog.cancel()
            })
        val alert = build.create()
        alert.setTitle("Update Note")
        alert.setView(update)
        alert.show()
    }


}
