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
import com.example.notesappfull.RomDB.Note
import com.example.notesappfull.RomDB.NoteDatabase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    lateinit var rcv : RecyclerView
    lateinit var edNewNote: EditText
    lateinit var btnSubmit: Button
    //lateinit var model: Model
    var TAG = "main"
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        edNewNote = findViewById(R.id.edNewNote)
        btnSubmit = findViewById(R.id.btnSubmit)
        rcv = findViewById(R.id.rcv)

        btnSubmit.setOnClickListener {

            if(edNewNote.text.isNotEmpty()){
                val note = hashMapOf (
                    "note" to edNewNote.text.toString()
                )
                db.collection("Notes").add(note)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        Toast.makeText(applicationContext, "data saved successfully!", Toast.LENGTH_SHORT).show()
                        getNote()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }else
                Toast.makeText(applicationContext, "please fill the missing entry!", Toast.LENGTH_SHORT).show()
            edNewNote.text.clear()
        }
    }


    fun getNote(){
        db.collection("Notes").get()
            .addOnSuccessListener { collection ->
                var NoteList= arrayListOf<List<Any>>()
                for (document in collection){
                    document.data.map { (k,v)->
                        NoteList.add(listOf(document.id,v))
                    }
                }
                rcv.adapter=RVadaptar(this,NoteList)
                rcv.layoutManager= LinearLayoutManager(this)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                Toast.makeText(applicationContext, "error!", Toast.LENGTH_SHORT).show()
            }
    }

    fun delNote(id: String) {
        db.collection("Notes").document(id).delete()
        getNote()
    }

    fun update(id: String) {
        val alert= androidx.appcompat.app.AlertDialog.Builder(this)
        alert.setTitle("update note")
        alert.setMessage("Enter your updated note below ")

        val editNote=EditText(this)
        editNote.hint="Enter new note"
        alert.setView(editNote)

        alert.setNegativeButton("Save" , DialogInterface.OnClickListener(){
                dialog, which ->
            if(editNote.text.isNotEmpty()){
                val note = hashMapOf (
                    "note" to editNote.text.toString()
                )
                db.collection("Notes").document(id).set(note, SetOptions.merge())
                getNote()
            }

        })//end setNegativeButton

        alert.setPositiveButton("Cancel" , DialogInterface.OnClickListener(){
                dialog, which -> dialog.cancel()
        })//end positiveButton

        alert.create().show()
    }
}




