package com.begi.fastbird

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


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
    var selectedItem by remember { mutableIntStateOf(0) }
    var currentScreen by remember { mutableStateOf("Home") }
    var cartItems by remember { mutableStateOf(listOf<String>()) }

    val hour = remember { java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY) }
    val greeting = when (hour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"}
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("FastBird") },
                navigationIcon = {
                    IconButton(onClick = { currentScreen = "Menu" }) { // ✅ make menu clickable
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$greeting Begi", // 👈 Greeting with name
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        IconButton(onClick = { currentScreen = "Profile" }) {
                            Icon(Icons.Filled.Person, contentDescription = "Profile")

                        }
                }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFADD8E6)) {
                val items = listOf(
                    Icons.Filled.Home to "Home",
                    Icons.Filled.Search to "Search",
                    Icons.Filled.ShoppingCart  to "Cart",
                    Icons.Filled.DateRange  to "Date"
                )

                items.forEachIndexed { index, (icon, label) ->
                    val isSelected = selectedItem == index
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
                                    .offset(y = offsetY)
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
                .background(Color(0xFFADD8E6)),
            contentAlignment = Alignment.Center
        ) {
            when (selectedItem) {
                0 -> {
                    when (currentScreen) {
                        "Home" -> Homescreen(
                            onAddToCart = { meal -> cartItems = cartItems + meal },
                            onDietClick = { currentScreen = "Diet" }
                        )
                        "Diet" -> DietManagerScreen()
                        "Profile" -> ProfileScreen(onBack = { currentScreen = "Home" })
                        "Menu" -> Text("📋 Menu (drawer placeholder)") // ✅ placeholder for now
                    }
                }
                1 -> Text("Search your Hotels 🔍")
                2 -> CartScreen(cartItems)
                3 -> Text("Mark The date")
            }
        }
    }
}

@Composable
fun ProfileScreen(onBack: () -> Unit) {
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

        TextButton(onClick = onBack) {
            Text("⬅ Back to Home")
        }
    }
}


@Composable

fun Homescreen(onAddToCart: (String) -> Unit, onDietClick: () -> Unit) {
    var showHotels by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Choose Hotel card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { showHotels = !showHotels }, // toggle expand
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

        // Show hotel list only if clicked
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

        // Other feature cards
        item {
            FeatureCard("Meal Tracker")
        }
        item {
            FeatureCard("Diet Manager",onClick=onDietClick)
        }
        item {
            SpendingHistoryCard()
        }
        item {
            FeatureCard("Weight Tracker")
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

@Composable
fun FeatureCard(title: String, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick?.invoke() }, // 👈 make clickable
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
                    IconButton(onClick = { onAddToCart(meal) }) { // ✅ FIXED
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Add to cart")
                    }
                }
            }
        }
    }
}


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

