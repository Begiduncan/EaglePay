package com.begi.eaglepay.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartScreen(cartItems: List<String>) {
    // Mock price list
    val prices = mapOf(
        "Grilled Chicken" to 500,
        "Burger" to 300,
        "Beef Steak" to 700,
        "Caesar salad" to 400
    )

    val total = cartItems.sumOf { prices[it] ?: 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🛒 Your Cart", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            Text("Cart is empty")
        } else {
            cartItems.forEach { item ->
                Text("- $item (${prices[item] ?: 0} Ksh)")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Total: $total Ksh", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Proceed to Pay")
                }
            }
        }
    }
}