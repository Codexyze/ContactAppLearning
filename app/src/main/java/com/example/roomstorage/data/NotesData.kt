package com.example.roomstorage.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
var id:Int?=null,
    var call:String,
    var name:String
)
