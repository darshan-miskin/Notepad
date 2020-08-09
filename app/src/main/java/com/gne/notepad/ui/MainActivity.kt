package com.gne.notepad.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gne.notepad.R
import com.gne.notepad.databinding.ActivityMainBinding
import com.gne.notepad.ui.Adapters.NotesAdapter
import com.gne.notepad.vm.NoteViewModel
import com.gne.notepad.vo.Note

class MainActivity : AppCompatActivity(),View.OnClickListener,NotesAdapter.OnNoteClickListener {

    private lateinit var binding:ActivityMainBinding
    private val newNoteActivityResultCode = 1
    private val updateNoteActivityResultCode = 1
    private lateinit var viewModel: NoteViewModel
    private lateinit var notesAdapter:NotesAdapter

    private val TAG=MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnFab.setOnClickListener(this)

        notesAdapter=NotesAdapter(this)
        binding.recyclerview.adapter=notesAdapter

        viewModel=ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { notes -> notes?.let {
            notesAdapter.setNotes(notes)
        } })


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_fab ->{
                val intent=Intent(this,NoteActivity::class.java)
                startActivityForResult(intent,newNoteActivityResultCode)
            }
        }
    }

    /**
     * NoteAdapter's OnNoteClickListener
     */
    override fun onClick(position: Int) {
        Toast.makeText(applicationContext,"TODO",Toast.LENGTH_SHORT).show()
//        val intent=Intent(this,NoteActivity::class.java)
//        intent.putExtra(NoteActivity.EXTRA_TITLE,notesAdapter.getNote(position).title)
//        intent.putExtra(NoteActivity.EXTRA_BODY,notesAdapter.getNote(position).body)
//        startActivityForResult(intent,updateNoteActivityResultCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val title=data?.getStringExtra(NoteActivity.EXTRA_TITLE)
        val body=data?.getStringExtra(NoteActivity.EXTRA_BODY)

        if (requestCode == newNoteActivityResultCode && resultCode == Activity.RESULT_OK) {
            if(title!=null && body!=null) {
                val note = Note(title, body)
                viewModel.insert(note)
            }
        }
        else if(requestCode == updateNoteActivityResultCode && resultCode == Activity.RESULT_OK){

        }
        else {
            Toast.makeText(applicationContext, "No data", Toast.LENGTH_LONG).show()
        }
    }
}