package com.gne.notepad.repo

import androidx.lifecycle.LiveData
import com.gne.notepad.room.NoteDao
import com.gne.notepad.vo.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes:LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insertNote(note)
    }
}