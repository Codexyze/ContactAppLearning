package com.example.roomstorage.domain.UseCases

import com.example.roomstorage.data.Contact
import com.example.roomstorage.domain.Repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val repository: NotesRepository) {
     fun invoke(): Flow<List<Contact>>{
        return repository.getAllNotes()
    }

}