package com.begi.eaglepay.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.begi.eaglepay.navigation.Home

@Composable
fun ProfileScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john@example.com") }
    var phone by remember { mutableStateOf("0712345678") }
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("👤 Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        if (isEditing) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { isEditing = false }) {
                    Text("Cancel")
                }
                Button(onClick = { isEditing = false }) {
                    Text("Save")
                }
            }
        } else {
            Text("Name: $name", fontSize = 18.sp)
            Text("Email: $email", fontSize = 18.sp)
            Text("Phone: $phone", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { isEditing = true }) {
                Text("Edit Profile")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = {
                navController.navigate(Home)
            }
        ) {
            Text("⬅ Back to Home")
        }
    }
}
