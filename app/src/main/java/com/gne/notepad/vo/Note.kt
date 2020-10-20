package com.gne.notepad.vo

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Note(@ColumnInfo(name = "nt_title") @Bindable var title:String,
                @ColumnInfo(name = "nt_body") @Bindable var body:String) : Parcelable, BaseObservable() {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nt_id")
    var id:Long?=null
    @Ignore
    var isSelected:Boolean=false
    @Ignore
    var position:Int=-1

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(body)
        parcel.writeValue(id)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}