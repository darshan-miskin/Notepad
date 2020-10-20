package com.gne.notepad.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private val updateNoteActivityResultCode = 2
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
            if(it.isEmpty()) {
                binding.recyclerview.visibility = View.GONE
                binding.layoutNew.visibility = View.VISIBLE
            }
            else {
                binding.layoutNew.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
            }
            notesAdapter.setNotes(it)
        } })
    }

    private fun resetDelete(){
        viewModel.notesToDelete.clear()
        viewModel.isDeleteMode=false
        notesAdapter.isDeleteMode=false
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete ->{
                viewModel.delete(viewModel.notesToDelete)
                Toast.makeText(applicationContext,"Delete note : ${viewModel.notesToDelete}",Toast.LENGTH_SHORT).show()
                for (i in viewModel.notesToDelete) {
                    notesAdapter.notifyItemRemoved(i.position)
                }
                resetDelete()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
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
    override fun onClick(position: Int, isSelected:Boolean) {
        if(viewModel.isDeleteMode){
            if(isSelected)
                viewModel.notesToDelete.add(notesAdapter.getNote(position))
            else
                viewModel.notesToDelete.remove(notesAdapter.getNote(position))
        }
        else {
            val intent = Intent(this, NoteActivity::class.java)
            intent.putExtra(NoteActivity.EXTRA_NOTE, notesAdapter.getNote(position))
            startActivityForResult(intent, updateNoteActivityResultCode)
        }

        if(viewModel.notesToDelete.isEmpty()) {
            resetDelete()
        }

    }

    override fun onLongClick(position: Int) {
        viewModel.isDeleteMode=true
        viewModel.notesToDelete.add(notesAdapter.getNote(position))
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
            val note=data?.getParcelableExtra<Note>(NoteActivity.EXTRA_NOTE)
            if(note!=null) {
                viewModel.update(note)
            }
        }
    }

    override fun onBackPressed() {
        if(viewModel.isDeleteMode){
            for (i in viewModel.notesToDelete) {
                notesAdapter.notifyItemChanged(i.position)
            }
            resetDelete()
        }
        else
            super.onBackPressed()
    }
}