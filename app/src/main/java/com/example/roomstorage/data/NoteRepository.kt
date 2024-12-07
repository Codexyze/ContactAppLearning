package com.example.roomstorage.data

import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesDao) {

    // Function to save or update a note
    suspend fun saveOrUpdate(contact: Contact) {
        notesDao.saveUpdateNote(contact)
    }

    // Function to retrieve all notes
    fun getAllNotes(): Flow<List<Contact>> {
        return notesDao.getAllNotes()
    }

    // Function to delete a note
    suspend fun delete(contact: Contact) {
        notesDao.deleteNote(contact)
    }
}