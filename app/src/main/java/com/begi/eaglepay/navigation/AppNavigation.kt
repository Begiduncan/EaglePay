package com.begi.eaglepay.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.begi.eaglepay.ui.screens.CartScreen
import com.begi.eaglepay.ui.screens.DietManagerScreen
import com.begi.eaglepay.ui.screens.HomeScreen
import com.begi.eaglepay.ui.screens.MainScreen
import com.begi.eaglepay.ui.screens.ProfileScreen
import com.begi.eaglepay.ui.screens.SplashScreen
import com.begi.eaglepay.viewmodels.CartViewModel
import kotlinx.serialization.Serializable

@Serializable object Home
@Serializable object Main
@Serializable object Profile
@Serializable object Cart
@Serializable object DietManager
@Serializable object Splash

@Composable
fun AppNavHost(
    viewModel: CartViewModel? = viewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var cartItems = viewModel!!.cartItems

    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier
    ) {
        composable<Home> {
            HomeScreen(
                onAddToCart = {
                    meal -> cartItems = cartItems /* TODO add meal */},
                onDietClick = {navController.navigate(DietManager) }
            )
        }

        composable<Main> {
            MainScreen(navController)
        }

        composable<Profile>{
            ProfileScreen(navController)
        }

        composable<Cart> {
            CartScreen(cartItems as List<String>)
        }

        composable<DietManager> {
            DietManagerScreen()
        }

        composable<Splash>{
            SplashScreen()
        }
    }}