package com.begi.eaglepay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.begi.eaglepay.navigation.Main
import com.begi.eaglepay.navigation.Splash
import kotlinx.coroutines.delay

@Composable
fun FastBirdApp(navController: NavHostController) {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(4000)
        showSplash = false
    }

    if (showSplash) {
       navController.navigate(Splash)
    } else {
        navController.navigate(Main)
    }
}