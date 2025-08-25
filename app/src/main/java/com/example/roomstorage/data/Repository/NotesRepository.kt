package com.example.roomstorage.data.Repository

import com.example.roomstorage.data.Contact
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun saveOrUpdate(contact: Contact)
    fun getAllNotes(): Flow<List<Contact>>
    suspend fun delete(contact: Contact)
}