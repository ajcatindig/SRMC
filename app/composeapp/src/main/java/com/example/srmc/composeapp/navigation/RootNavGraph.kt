package com.example.srmc.composeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.srmc.composeapp.ui.screens.home.HomeScreen

@Composable
fun RootNavGraph(navController : NavHostController)
{
    NavHost(navController = navController,
            route = Graph.ROOT,
            startDestination = Graph.AUTHENTICATION)
    {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val WELCOME = "welcome_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "main_graph"
    const val DETAILS = "details_graph"
}