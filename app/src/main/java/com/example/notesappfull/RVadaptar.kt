package com.example.notesappfull


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.RomDB.Note
import com.example.notesappfull.databinding.ItemRowBinding



class RVadaptar(val acticvity: Fragment_list, val NoteList:List<Note>):
    RecyclerView.Adapter<RVadaptar.ItemHold>() {


    class ItemHold (val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHold {
        return ItemHold(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
            parent,false)
        )

    }

    override fun onBindViewHolder(holder: ItemHold, position: Int) {
        val noteObject = NoteList[position]
        var id = NoteList[position].id
        var note = NoteList[position].note

        holder.binding.apply {
            txtNote.text = "Note $id: "
            txt2.text = note

            ibEditNote.setOnClickListener() {
                acticvity.goFragmentUpdate(id)
            }

            ibDeleteNote.setOnClickListener() {
                acticvity.delNote(noteObject)
            }

        }
    }

    override fun getItemCount() = NoteList.size

}
