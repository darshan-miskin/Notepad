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

    private var arrayList = emptyList<Note>()
    var isDeleteMode = false
        internal set

    interface OnNoteClickListener {
        fun onClick(position: Int, isSelected: Boolean)
        fun onLongClick(position: Int)
    }

    internal fun setNotes(notes: List<Note>) {
        this.arrayList = notes
        notifyDataSetChanged()
    }

    internal fun getNote(position: Int): Note {
        return arrayList.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutRecyclerItemBinding>(inflater, R.layout.layout_recycler_item, parent, false)
        return NotesHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.note = arrayList.get(position)
        arrayList.get(position).position=position

        holder.binding.chkSelect.visibility = if (isDeleteMode) {
            if (arrayList.get(position).isSelected)
                holder.binding.chkSelect.isChecked = true
            View.VISIBLE
        }
        else {
            arrayList.get(position).isSelected=false
            View.GONE
        }

        holder.binding.chkSelect.isChecked = arrayList.get(position).isSelected
    }

    inner class NotesHolder(val binding: LayoutRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardMain.setOnClickListener {
                if(isDeleteMode){
                    arrayList.get(adapterPosition).isSelected=!arrayList.get(adapterPosition).isSelected
                    binding.chkSelect.isChecked=arrayList.get(adapterPosition).isSelected
                    binding.chkSelect.visibility=if(binding.chkSelect.visibility==View.VISIBLE) View.GONE else View.VISIBLE
                }
                onNoteClickListener.onClick(adapterPosition,arrayList.get(adapterPosition).isSelected)
            }
            binding.cardMain.setOnLongClickListener {
                isDeleteMode = true
                arrayList.get(adapterPosition).isSelected=true
                binding.chkSelect.isChecked=arrayList.get(adapterPosition).isSelected
                binding.chkSelect.visibility=View.VISIBLE
                onNoteClickListener.onLongClick(adapterPosition)
                true
            }
        }
    }
}