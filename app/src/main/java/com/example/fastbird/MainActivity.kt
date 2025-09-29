package com.example.fastbird

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.animation.core.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                FastBirdApp()
            }
        }
    }
}

@Composable
fun FastBirdApp() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(4000) // show splash longer since it has animations
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        MainScreen()
    }
}

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

    // Shimmer animation (light passing effect)
    val shimmerTranslate = rememberInfiniteTransition()
    val shimmerX by shimmerTranslate.animateFloat(
        initialValue = -200f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White,
            Color.White.copy(alpha = 0.3f)
        ),
        start = Offset(shimmerX, 0f),   // use geometry.Offset
        end = Offset(shimmerX + 200f, 200f)
    )


    // Snake-like bounce for tagline
    val infiniteTransition = rememberInfiniteTransition()
    val bounce by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E88E5)), // Blue background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Shimmered FastBird text
            Text(
                text = displayedText,
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge.copy(
                    brush = shimmerBrush
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tagline with bounce
            Text(
                text = "Increase your pay speed, Save Time!",
                fontSize = 18.sp,
                color = Color.Yellow,
                modifier = Modifier.offset(y = bounce.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("FastBird") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: open drawer or menu */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: go to profile */ }) {
                        Icon(Icons.Filled.Person, contentDescription = "You")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFFFD1DC)) {
                val items = listOf(
                    Icons.Filled.Home to "Home",
                    Icons.Filled.Search to "Search",
                    Icons.Filled.ShoppingCart  to "Cart",
                    Icons.Filled.DateRange  to "Date"
                )

                items.forEachIndexed { index, (icon, label) ->
                    val isSelected = selectedItem == index

                    // animate squish offset
                    val offsetY by animateDpAsState(
                        targetValue = if (isSelected) 4.dp else 0.dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedItem = index },
                        icon = {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .offset(y = offsetY) // 👈 rubber squish effect
                            ) {
                                Icon(imageVector = icon, contentDescription = label)
                            }
                        },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFD1DC)),
            contentAlignment = Alignment.Center
        ) {
            when (selectedItem) {
                0 -> Text("Welcome to FastBird 🚀")
                1 -> Text("Search your Hotels 🔍")
                2 -> Text("Add to Cart")
                3 -> Text("Mark The date")
            }
        }
    }
}
