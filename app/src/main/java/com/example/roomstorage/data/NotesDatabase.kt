package com.example.roomstorage.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class NotesDatabase :RoomDatabase(){
   abstract fun dao():NotesDao

}