package com.begi.eaglepay.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.begi.eaglepay.R
import com.begi.eaglepay.navigation.AppNavHost
import com.begi.eaglepay.navigation.DietManager
import com.begi.eaglepay.navigation.Profile
import kotlinx.serialization.Serializable
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    val hour = remember { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val greeting = when (hour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }

    val navItems = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Cart,
            BottomNavItem.Diet
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("FastBird") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // TODO implement menu screen
                        }
                    ) {
                        Icon(
                          painter = painterResource(R.drawable.baseline_menu_24),
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$greeting Begi",
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        IconButton(onClick = {
                           navController.navigate(Profile)
                        }) {
                            Icon(
                               painter = painterResource(R.drawable.outline_lab_profile_24),
                                contentDescription = "Profile"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFADD8E6)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItems.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == item.route.toString() } == true
                    NavigationBarItem(
                        icon = {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = if (isSelected) item.selectedIcon else item.unselectedIcon),
                                contentDescription = stringResource(id = item.titleRes)
                            )
                        },
                        label = { Text(stringResource(id = item.titleRes)) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to avoid building up a large back stack
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when re-selecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
    ) { paddingValues ->
        AppNavHost(
            null, navController, modifier = Modifier.padding(paddingValues)
        )
    }
}

sealed class BottomNavItem(
    val route: @Serializable Any,
    val titleRes: Int,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    data object Home : BottomNavItem(
        route = com.begi.eaglepay.navigation.Home,
        titleRes = R.string.bottom_nav_home,
        selectedIcon = R.drawable.outline_home_24,
        unselectedIcon = R.drawable.outline_home_24
    )
    data object Cart : BottomNavItem(
        route = com.begi.eaglepay.navigation.Cart,
        titleRes = R.string.bottom_nav_cart,
        selectedIcon = R.drawable.outline_shopping_cart_24,
        unselectedIcon = R.drawable.outline_shopping_cart_24
    )
    data object Diet : BottomNavItem(
        route = DietManager,
        titleRes = R.string.bottom_nav_dietmanager,
        selectedIcon = R.drawable.outline_lab_profile_24,
        unselectedIcon = R.drawable.outline_lab_profile_24
    )
}