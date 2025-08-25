package com.example.roomstorage.Presentation.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomstorage.data.Contact
import com.example.roomstorage.data.Repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NotesRepository) : ViewModel() {

    // StateFlow to hold the list of notes
    private val _notes = MutableStateFlow<List<Contact>>(emptyList())
    val notes: StateFlow<List<Contact>> = _notes

    init {
        fetchNotes()
    }

    // Fetch all notes from the repository
    private fun fetchNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect {
                _notes.value = it
            }
        }
    }

    // Save or update a note
    fun saveOrUpdate(contact: Contact) {
        viewModelScope.launch {
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
