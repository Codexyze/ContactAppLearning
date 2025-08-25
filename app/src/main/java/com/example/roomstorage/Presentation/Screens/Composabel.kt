package com.example.roomstorage.Presentation.Screens

//
//@Composable
//fun NotesApp(viewModel: NotesViewModel) {
//    var input by remember { mutableStateOf(TextFieldValue("")) }
//    var name by remember { mutableStateOf(TextFieldValue("")) }
//
//    // Observe the notes list from the ViewModel
//    val notesList by viewModel.notes.collectAsState()
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Spacer(modifier = Modifier.height(32.dp)) // Add spacing at the top
//
//        // Input Fields
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Enter Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp)) // Spacing between fields
//
//        OutlinedTextField(
//            value = input,
//            onValueChange = { input = it },
//            label = { Text("Enter Number") },
//            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp)) // Spacing before Save button
//
//        // Save Button
//        Button(
//            onClick = {
//                if (name.text.isNotBlank() && input.text.isNotBlank()) {
//                    viewModel.saveOrUpdate(
//                        Contact(
//                            name = name.text.trim(),
//                            call = input.text.trim()
//                        )
//                    )
//                    name = TextFieldValue("") // Clear fields after saving
//                    input = TextFieldValue("")
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Save")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // List of Notes
//        LazyColumn {
//            items(notesList) { contact ->
//                ContactItem(
//                    contact = contact,
//                    onDelete = { viewModel.delete(it) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ContactItem(contact: Contact, onDelete: (Contact) -> Unit) {
//    var isPermissionGranted by remember { mutableStateOf(false) }
//
//    // Permission launcher for CALL_PHONE
//    val requestPermissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { granted ->
//        isPermissionGranted = granted
//    }
//
//    val context = LocalContext.current
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable {
//                if (isPermissionGranted) {
//                    makePhoneCall(context, contact.call)
//                } else {
//                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
//                }
//            },
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column(modifier = Modifier.weight(1f)) {
//            Text(
//                text = "Name: ${contact.name}",
//
//            )
//            Text(
//                text = "Number: ${contact.call}",
//
//            )
//        }
//
//        Button(onClick = { onDelete(contact) }) {
//            Text("Delete")
//        }
//    }
//}
//
//fun makePhoneCall(context: Context, phoneNumber: String) {
//    try {
//        val callIntent = Intent(Intent.ACTION_CALL).apply {
//            this.setData = Uri.parse("tel:$phoneNumber")
//        }
//        context.startActivity(callIntent)
//    } catch (e: SecurityException) {
//        Toast.makeText(
//            context,
//            "Permission Denied! Unable to make the call.",
//            Toast.LENGTH_SHORT
//        ).show()
//    }
//}
