package com.example.roomstorage.data

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
   fun getDb(context: Context): NotesDatabase {
      return Room.databaseBuilder(
           context = context,
           NotesDatabase::class.java, "note_db"
       ).build()
   }
}