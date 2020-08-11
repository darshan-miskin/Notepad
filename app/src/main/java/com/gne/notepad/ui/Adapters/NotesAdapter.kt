package com.gne.notepad.ui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gne.notepad.R
import com.gne.notepad.databinding.LayoutRecyclerItemBinding
import com.gne.notepad.vo.Note

class NotesAdapter internal constructor(private val onNoteClickListener: OnNoteClickListener) : RecyclerView.Adapter<NotesAdapter.NotesHolder>() {

    private var arrayList= emptyList<Note>()

    interface OnNoteClickListener{
        fun onClick(position: Int)
        fun onLongClick(note: Note)
    }

    internal fun setNotes(notes:List<Note>){
        this.arrayList=notes
        notifyDataSetChanged()
    }

    internal fun getNote(position: Int):Note{
        return arrayList.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<LayoutRecyclerItemBinding>(inflater, R.layout.layout_recycler_item,parent,false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.note=arrayList.get(position)
    }

    inner class NotesHolder(val binding: LayoutRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener { onNoteClickListener.onClick(adapterPosition)}
            binding.root.setOnLongClickListener{
                onNoteClickListener.onLongClick(arrayList.get(adapterPosition))
                true
            }
        }
    }
}