package com.example.roomstorage.data

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

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
class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
