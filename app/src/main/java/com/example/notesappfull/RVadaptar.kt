package com.example.notesappfull

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.databinding.ItemRowBinding



class RVadaptar(val acticvity: MainActivity, var lis: List<Note>):
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
        val notes = lis[position]
        holder.binding.apply {
            txtNote.text = notes.noteText
            if(position%2==0){llNoteHolder.setBackgroundColor(Color.GRAY)}
            ibEditNote.setOnClickListener {
                acticvity.raiseDialog(notes.id)
            }
            ibDeleteNote.setOnClickListener {
                acticvity.deleteNote(notes.id)
            }

        }
    }

    override fun getItemCount() = lis.size
}
