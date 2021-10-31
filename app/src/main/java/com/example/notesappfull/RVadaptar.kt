package com.example.notesappfull


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.databinding.ItemRowBinding



class RVadaptar(val acticvity: MainActivity,val list:List<Note>):
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
        val notes = list[position]
        holder.binding.apply {
            txtNote.text = notes.noteText
            ibEditNote.setOnClickListener {
                acticvity.dilogfun(notes.id)
            }
            ibDeleteNote.setOnClickListener {
                acticvity.model.deleteNote(notes.id)
            } } }

    override fun getItemCount() = list.size

}
