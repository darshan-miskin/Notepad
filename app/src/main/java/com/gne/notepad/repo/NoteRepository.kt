package com.gne.notepad.repo

import androidx.lifecycle.LiveData
import com.gne.notepad.db.NoteDao
import com.gne.notepad.vo.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes:LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun update(note: Note){
        noteDao.updateNote(note)
    }
}