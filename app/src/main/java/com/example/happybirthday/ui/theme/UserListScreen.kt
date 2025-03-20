@file:OptIn(
    ExperimentalMaterial3Api::class
)


package com.example.importsbywam.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.importsbywam.viewmodel.UserViewModel
import com.example.importsbywam.model.User



@Composable
fun UserListScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel())
 {
    val users by userViewModel.users.collectAsState()

    // Trigger fetch on screen launch
    LaunchedEffect(Unit) {
        userViewModel.fetchUsers()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Users List") })
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(users) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${user.name}")
            Text(text = "Email: ${user.email}")
            Text(text = "Phone: ${user.phone}")
            Text(text = "Type: ${user.user_type}")
        }
    }
}
