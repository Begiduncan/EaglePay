package com.begi.eaglepay.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DietManagerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Diet Manager", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        // ✅ Calorie Progress
        val caloriesConsumed = 1800
        val calorieTarget = 2200
        val progress = caloriesConsumed.toFloat() / calorieTarget.toFloat()

        Text("Calories: $caloriesConsumed / $calorieTarget")
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = Color(0xFF4CAF50)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // ✅ Pie chart for Macronutrients
        Text("Macronutrient Breakdown")
        Spacer(modifier = Modifier.height(10.dp))
        MacroPieChart(
            carbs = 50,
            protein = 30,
            fat = 20
        )
    }
}

@Composable
fun MacroPieChart(carbs: Int, protein: Int, fat: Int) {
    val total = carbs + protein + fat
    val carbsAngle = 360f * carbs / total
    val proteinAngle = 360f * protein / total
    val fatAngle = 360f * fat / total

    Canvas(
        modifier = Modifier
            .size(180.dp)
            .padding(8.dp)
    ) {
        var startAngle = 0f

        drawArc(
            color = Color(0xFFFFC107),
            startAngle = startAngle,
            sweepAngle = carbsAngle,
            useCenter = true
        )
        startAngle += carbsAngle

        drawArc(
            color = Color(0xFF2196F3),
            startAngle = startAngle,
            sweepAngle = proteinAngle,
            useCenter = true
        )
        startAngle += proteinAngle

        drawArc(
            color = Color(0xFFF44336),
            startAngle = startAngle,
            sweepAngle = fatAngle,
            useCenter = true
        )
    }
}
