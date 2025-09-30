package com.begi.eaglepay.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.begi.eaglepay.R

@Composable
fun FeatureCard(title: String, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick?.invoke() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun HotelCard(hotelName: String, meals: List<String>, onAddToCart: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = hotelName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            meals.forEach { meal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = meal)
                    IconButton(onClick = { onAddToCart(meal) }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_shopping_cart_24),
                            contentDescription = "Add to cart"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpendingHistoryCard() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Weekly", "Monthly", "Yearly")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Spending History",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            when (selectedTab) {
                0 -> SpendingGraph(
                    dataPoints = listOf(500, 1200, 900, 1500, 2000, 1700, 2200),
                    labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"),
                    yLabel = "KES",
                    title = "Weekly Spending"
                )
                1 -> SpendingGraph(
                    dataPoints = List(30) { (800..2500).random() },
                    labels = (1..30).map { it.toString() },
                    yLabel = "KES",
                    title = "Monthly Spending"
                )
                2 -> SpendingGraph(
                    dataPoints = listOf(12000, 15000, 11000, 18000, 20000, 17000, 25000, 22000, 19000, 21000, 23000, 24000),
                    labels = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"),
                    yLabel = "KES",
                    title = "Yearly Spending"
                )
            }
        }
    }
}

@Composable
fun SpendingGraph(
    dataPoints: List<Int>,
    labels: List<String>,
    yLabel: String,
    title: String
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(8.dp)
    ) {
        val maxValue = (dataPoints.maxOrNull() ?: 1).toFloat()
        val stepX = size.width / (dataPoints.size - 1).coerceAtLeast(1)
        val scaleY = size.height / maxValue

        val gridLinesY = 5
        val gridLinesX = dataPoints.size - 1

        // Horizontal grid + Y labels
        for (i in 0..gridLinesY) {
            val y = size.height - (i * size.height / gridLinesY)
            val value = (i * maxValue / gridLinesY).toInt()

            drawLine(
                color = Color.DarkGray,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1.5f
            )

            drawContext.canvas.nativeCanvas.drawText(
                "$yLabel $value",
                0f,
                y,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 30f
                }
            )
        }

        // Vertical grid + X labels
        for (i in 0..gridLinesX) {
            val x = i * stepX

            drawLine(
                color = Color.DarkGray,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1.5f
            )

            if (i < labels.size) {
                drawContext.canvas.nativeCanvas.drawText(
                    labels[i],
                    x,
                    size.height + 30f,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 28f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }

        // Line graph
        for (i in 0 until dataPoints.size - 1) {
            val x1 = i * stepX
            val y1 = size.height - (dataPoints[i] * scaleY)
            val x2 = (i + 1) * stepX
            val y2 = size.height - (dataPoints[i + 1] * scaleY)

            drawLine(
                color = Color.Blue,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 4f
            )
        }

        // Points
        dataPoints.forEachIndexed { index, value ->
            val x = index * stepX
            val y = size.height - (value * scaleY)

            drawCircle(
                color = Color.Red,
                radius = 8f,
                center = Offset(x, y)
            )
        }
    }
}