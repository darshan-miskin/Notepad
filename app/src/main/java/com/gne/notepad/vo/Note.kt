package com.gne.notepad.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nt_id") val id:Int,
                @ColumnInfo(name = "nt_title") val title:String,
                @ColumnInfo(name = "nt_body") val body:String)