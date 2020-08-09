package com.gne.notepad.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.gne.notepad.R
import com.gne.notepad.databinding.ActivityNoteBinding
import com.gne.notepad.vo.Note

class NoteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNoteBinding
    private val TAG=NoteActivity::class.simpleName

    companion object {
        const val EXTRA_TITLE = "com.gne.notepad.ui.noteactivity.TITLE"
        const val EXTRA_BODY = "com.gne.notepad.ui.noteactivity.BODY"
        const val EXTRA_NOTE = "com.gne.notepad.ui.noteactivity.NOTE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_note)

        val note:Note?=intent.getParcelableExtra(EXTRA_NOTE)

        if(note!=null) {
            binding.note = note
        }
    }

    override fun onBackPressed() {
        val body=binding.txtBody.text.toString()
        val title=binding.txtTitle.text.toString()
        val intent=Intent()

        val result=if(title.isEmpty() && body.isEmpty())
            Activity.RESULT_CANCELED
        else {
            intent.putExtra(EXTRA_TITLE,title)
            intent.putExtra(EXTRA_BODY,body)
            Activity.RESULT_OK
        }

        setResult(result,intent)
        finish()
    }
}