package com.gne.notepad.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gne.notepad.R
import com.gne.notepad.databinding.ActivityMainBinding
import com.gne.notepad.ui.Adapters.NotesAdapter
import com.gne.notepad.vo.Note

class MainActivity : AppCompatActivity(),View.OnClickListener,NotesAdapter.OnNoteClickListener {

    private lateinit var binding:ActivityMainBinding
    private val arrayList=ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnFab.setOnClickListener(this)

        val notesAdapter=NotesAdapter(arrayList,this)
        binding.recyclerview.adapter=notesAdapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_fab ->{
                Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * NoteAdapter's OnNoteClickListener
     */
    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }
}