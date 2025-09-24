package com.example.fastbird

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastbird.ui.theme.FastBirdTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastBirdTheme {
                App()

            }
        }
    }
}

@Composable
fun App() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        showSplash = false

    }
    if (showSplash) {
        FastBirdSplashScreen()
    } else {
        MenuScreen()
    }
}

@Composable
fun FastBirdSplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB))
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "FastBird", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FsTB Menu") })
        }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.coffee),
                contentDescription = "background image",
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxSize()
                    .background(Color(0xFFF1F1F1))
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Menu", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {/*TODO: Navigate to Game*/ }) {
                    Text("Settings")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {/* TODO: Navigate to Settings */ }) {
                    Text("Settings")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {/*TODO:Exit App */ }) {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    FastBirdTheme {
       MenuScreen()
    }
}
