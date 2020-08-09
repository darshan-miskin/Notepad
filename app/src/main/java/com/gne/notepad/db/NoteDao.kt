package com.gne.notepad.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gne.notepad.vo.Note

@Dao
interface NoteDao {
    @Query("Select * from note")
    fun getAllNotes():LiveData<List<Note>>

    @Query("Select nt_id, nt_title, nt_body from note where nt_id=:id")
    fun getNote(id:Int):LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNote(note:Note):Long

    @Query("delete from note where nt_id=:id")
    suspend fun deleteNote(id:Int)
}