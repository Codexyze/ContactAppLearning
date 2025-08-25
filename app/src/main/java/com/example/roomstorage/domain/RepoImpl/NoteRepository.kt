package com.example.roomstorage.domain.RepoImpl

import com.example.roomstorage.data.Contact
import com.example.roomstorage.data.NotesDatabase
import com.example.roomstorage.data.Repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NotesRepositoryImp @Inject constructor(private  val database: NotesDatabase): NotesRepository{
    override suspend fun saveOrUpdate(contact: Contact) {
        database.dao().saveUpdateNote(contact)
    }

    override fun getAllNotes(): Flow<List<Contact>> {
      return database.dao().getAllNotes()
    }

    override suspend fun delete(contact: Contact) {
        database.dao().deleteNote(note = contact)
    }

}