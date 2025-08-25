package com.example.roomstorage.domain.UseCases

import com.example.roomstorage.data.Contact
import com.example.roomstorage.data.Repository.NotesRepository
import javax.inject.Inject

class SaveOrUpdateUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend fun invoke(contact: Contact){
        repository.saveOrUpdate(contact = contact)
    }
}