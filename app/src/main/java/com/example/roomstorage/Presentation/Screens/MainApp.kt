package com.example.roomstorage.Presentation.Screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomstorage.Presentation.Viewmodels.NotesIntent
import com.example.roomstorage.Presentation.Viewmodels.NotesViewModel
import com.example.roomstorage.data.Contact


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(viewModel: NotesViewModel = hiltViewModel()) {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    val sizeAnimination = rememberSaveable { mutableStateOf(false) }
    val sizeAniminationTransition = animateDpAsState(
        targetValue = if(sizeAnimination.value) 45.dp else 12.dp,
        animationSpec = tween(1000),
        finishedListener = {
            sizeAnimination.value = false
        }
    )
    val colourState = rememberSaveable { mutableStateOf(false) }
    val colourAniminate = animateColorAsState(
        targetValue = if(colourState.value) Color.Cyan else MaterialTheme.colorScheme.primary,
        animationSpec = tween(500),
        finishedListener = {
            colourState.value = false
        }
    )



    // Observe the notes list from the ViewModel
    val notesList by viewModel.notes.collectAsState()

    // Load initial notes
    LaunchedEffect(Unit) {
        viewModel.OnInent(NotesIntent.LoadNotes)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📒 Call Book") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colourAniminate.value,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // Input Fields inside a Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Enter Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("Enter Number") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (name.text.isNotBlank() && input.text.isNotBlank()) {
                                val contact = Contact(
                                    name = name.text.trim(),
                                    call = input.text.trim()
                                )
                                sizeAnimination.value= true
                                colourState.value = true
                                viewModel.OnInent(NotesIntent.SaveNote(contact))
                                name = TextFieldValue("")
                                input = TextFieldValue("")

                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(sizeAniminationTransition.value),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colourAniminate.value
                        )
                    ) {


                        Text("💾 Save Contact")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Saved Contacts",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(notesList) { contact ->
                    ContactItem(
                        contact = contact,
                        onDelete = { viewModel.OnInent(NotesIntent.DeleteNote(it)) }
                    )
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onDelete: (Contact) -> Unit) {
    var isPermissionGranted by remember { mutableStateOf(false) }

    // Permission launcher for CALL_PHONE
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> isPermissionGranted = granted }

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (isPermissionGranted) {
                    makePhoneCall(context, contact.call)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = contact.call,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedButton(
                onClick = { onDelete(contact) },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("🗑 Delete")
            }
        }
    }
}


fun makePhoneCall(context: Context, phoneNumber: String) {
    try {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            //this.setData = Uri.parse("tel:$phoneNumber")
        }
        callIntent.setData(Uri.parse("tel:$phoneNumber"))
        context.startActivity(callIntent)
    } catch (e: SecurityException) {
        Toast.makeText(
            context,
            "Permission Denied! Unable to make the call.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
