package com.begi.eaglepay.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.begi.eaglepay.ui.components.FeatureCard
import com.begi.eaglepay.ui.components.HotelCard
import com.begi.eaglepay.ui.components.SpendingHistoryCard

@Composable
fun HomeScreen(onAddToCart: (String) -> Unit, onDietClick: () -> Unit) {
    var showHotels by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { showHotels = !showHotels },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Text(
                    text = "Choose Hotel",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        if (showHotels) {
            item {
                HotelCard(
                    hotelName = "Mid-city",
                    meals = listOf("Grilled Chicken", "Burger"),
                    onAddToCart = onAddToCart
                )
            }
            item {
                HotelCard(
                    hotelName = "Lakers Inn",
                    meals = listOf("Beef Steak", "Caesar salad"),
                    onAddToCart = onAddToCart
                )
            }
            item {
                HotelCard(
                    hotelName = "Clan Dishes",
                    meals = listOf("Pilau", "Fish Fry"),
                    onAddToCart = onAddToCart
                )
            }
        }

        item { FeatureCard("Meal Tracker") }
        item { FeatureCard("Diet Manager", onClick = onDietClick) }
        item { SpendingHistoryCard() }
        item { FeatureCard("Weight Tracker") }
    }
}