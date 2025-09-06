package com.example.roomstorage.Presentation.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomstorage.data.Contact
import com.example.roomstorage.domain.Repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NotesRepository) : ViewModel() {

    // StateFlow to hold the list of notes
    private val _notes = MutableStateFlow<List<Contact>>(emptyList())
    val notes: StateFlow<List<Contact>> = _notes


    fun OnInent(intent: NotesIntent){
        when(intent){
            is NotesIntent.SaveNote -> {
                saveOrUpdate(intent.contact)

            }
            is NotesIntent.DeleteNote -> {
                delete(intent.contact)

            }
            is NotesIntent.LoadNotes -> {
                fetchNotes()

            }
        }

    }

    // Fetch all notes from the repository
    private fun fetchNotes() {
        viewModelScope.launch (Dispatchers.IO){
            repository.getAllNotes().collect {
                _notes.value = it
            }
        }
    }

    // Save or update a note
    fun saveOrUpdate(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveOrUpdate(contact)
        }
    }

    // Delete a note
    fun delete(contact: Contact) {
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
}

sealed interface NotesIntent {
    object LoadNotes : NotesIntent
    data class SaveNote(val contact: Contact) : NotesIntent
    data class DeleteNote(val contact: Contact) : NotesIntent
}
