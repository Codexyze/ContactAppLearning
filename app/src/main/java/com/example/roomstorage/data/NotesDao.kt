package com.example.roomstorage.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface NotesDao {
    @Upsert
    suspend fun saveUpdateNote(contact: Contact)
    @Query("SELECT * FROM notes_table ")
    fun  getAllNotes():Flow<List<Contact>>
    @Delete
     suspend  fun deleteNote(note: Contact)
}