package com.gne.notepad.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gne.notepad.repo.NoteRepository
import com.gne.notepad.db.NoteDatabase
import com.gne.notepad.vo.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {
    private val repository:NoteRepository
    val allNotes:LiveData<List<Note>>
    var notesToDelete=ArrayList<Note>()
    var isDeleteMode = false

    init {
        val dao=NoteDatabase.getDatabase(application,viewModelScope).NoteDao()
        repository= NoteRepository(dao)
        allNotes=repository.allNotes
    }

    fun insert(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }

    fun update(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.update(note)
    }

    fun delete(note: List<Note>)=viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }
}