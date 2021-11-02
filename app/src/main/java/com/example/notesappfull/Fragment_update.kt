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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import com.example.notesappfull.RomDB.Note
import com.example.notesappfull.RomDB.NoteDatabase


class Fragment_update : Fragment() {
    private val modelCommunicator: Model by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        var idN:Int?=null
        val edNotes = view.findViewById<EditText>(R.id.edNots)
        val btnSumbmit = view.findViewById<Button>(R.id.btnSubmit)

        btnSumbmit.setOnClickListener() {
            modelCommunicator.getId.observe(viewLifecycleOwner,  Observer { t->
                idN=t.toString().toInt()
            })
            if (edNotes.text.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    this.let {
                        val NoteDB = NoteDatabase.getInstance(requireContext())
                        NoteDB.getNotesDao().updateNote(Note(idN.toString().toInt(), edNotes.text.toString()))
                    }
                }//end CoroutineScope
                Navigation.findNavController(view).navigate(R.id.fragment_list)
            }//end if
            else
                Toast.makeText(requireContext(), "please fill the missing entry!", Toast.LENGTH_SHORT).show()
        }//lis
        return view
    }//

}//end class