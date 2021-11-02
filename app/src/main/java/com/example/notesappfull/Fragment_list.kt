package com.example.notesappfull

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.RomDB.Note
import com.example.notesappfull.RomDB.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Fragment_list : Fragment() {

    private val modelCommunicator: Model by activityViewModels()
    lateinit var rcv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_list, container, false)

        rcv = view.findViewById(R.id.rcv)
        val edNotes=view.findViewById<EditText>(R.id.edNewNote)
        val btnSumbmit=view.findViewById<Button>(R.id.btnSubmit)
        getNotes()
        btnSumbmit.setOnClickListener(){
            val NoteDB= NoteDatabase.getInstance(requireContext())
            if(edNotes.text.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    NoteDB.getNotesDao().insertNote(Note(0, edNotes.text.toString()))
                    getNotes()
                }
                Toast.makeText(requireContext(), "data saved successfully!", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(requireContext(), "please fill the missing entry!", Toast.LENGTH_SHORT).show()
            edNotes.text.clear()
        }//end btnSum listener

        return view
    }

    fun getNotes(){
        CoroutineScope(Dispatchers.IO).launch{
            val NoteDB=NoteDatabase.getInstance(requireContext())
            withContext(Dispatchers.Main){
                rcv.adapter=RVadaptar(this@Fragment_list, NoteDB.getNotesDao().getAll())
                rcv.layoutManager= LinearLayoutManager(requireContext())
            }
        }
    }//end getNote()

    fun goFragmentUpdate(id:Int){
        modelCommunicator.setId(id)
        Navigation.findNavController(requireView()).navigate(R.id.fragment_update)
    }

    fun delNote(notesTable: Note){
        CoroutineScope(Dispatchers.IO).launch {
            val NoteDB=NoteDatabase.getInstance(requireContext())
            NoteDB.getNotesDao().delNote(Note(notesTable.id,notesTable.note))
            getNotes()
        }
    }
}