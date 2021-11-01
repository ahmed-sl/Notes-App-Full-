package com.example.notesappfull


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.RomDB.Note
import com.example.notesappfull.databinding.ItemRowBinding



class RVadaptar(val acticvity: MainActivity, val NoteList:ArrayList<List<Any>>):
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
        val id = NoteList[position][0]
        val notes = NoteList[position][1]
        holder.binding.apply {
            txtNote.text = "Note $position:"
            txt2.text = notes.toString()
            ibEditNote.setOnClickListener {
                acticvity.update(id.toString())
            }
            ibDeleteNote.setOnClickListener {
                acticvity.delNote(id.toString())
            }
        }}

    override fun getItemCount() = NoteList.size

}
