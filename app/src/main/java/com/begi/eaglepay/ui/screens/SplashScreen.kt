package com.begi.eaglepay.ui.screens

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val fullText = "Fast⚡Bird"
    var displayedText by remember { mutableStateOf("") }

    // Typing animation
    LaunchedEffect(Unit) {
        fullText.forEachIndexed { index, _ ->
            displayedText = fullText.substring(0, index + 1)
            delay(120)
        }
    }

    // Shimmer animation
    val shimmerTranslate = rememberInfiniteTransition(label = "shimmer")
    val shimmerX by shimmerTranslate.animateFloat(
        initialValue = -200f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "shimmerX"
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White,
            Color.White.copy(alpha = 0.3f)
        ),
        start = Offset(shimmerX, 0f),
        end = Offset(shimmerX + 200f, 200f)
    )

    // Snake-like bounce for tagline
    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val bounce by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "bounce"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E88E5)), // Blue background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = displayedText,
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge.copy(
                    brush = shimmerBrush
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Increase your pay speed, Save Time!",
                fontSize = 18.sp,
                color = Color.Yellow,
                modifier = Modifier.offset(y = bounce.dp)
            )
        }
    }
}