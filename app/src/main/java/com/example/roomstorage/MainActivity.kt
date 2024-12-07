package com.example.roomstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.roomstorage.data.DatabaseInstance
import com.example.roomstorage.data.NotesApp
import com.example.roomstorage.data.NotesRepository
import com.example.roomstorage.data.NotesViewModel
import com.example.roomstorage.data.NotesViewModelFactory
import com.example.roomstorage.ui.theme.RoomStorageTheme

class MainActivity : ComponentActivity() {

    private val viewModel: NotesViewModel by lazy {
        val dao = DatabaseInstance.getDb(this).dao()
        val repository = NotesRepository(dao)
        ViewModelProvider(this, NotesViewModelFactory(repository))[NotesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomStorageTheme {
                NotesApp(viewModel)
            }
        }
    }
}

