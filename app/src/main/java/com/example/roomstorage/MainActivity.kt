package com.example.roomstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.roomstorage.Presentation.Screens.MainApp
import com.example.roomstorage.ui.theme.RoomStorageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomStorageTheme {
               MainApp()
            }
        }
    }
}

