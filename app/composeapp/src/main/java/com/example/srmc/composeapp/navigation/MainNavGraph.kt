package com.example.srmc.composeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.srmc.composeapp.component.bottombar.BottomBarHomeItem
import com.example.srmc.composeapp.ui.screens.home.HomeScreen

@Composable
fun MainNavGraph(navController : NavHostController)
{
    NavHost(
            navController = navController,
            route = Graph.HOME,
            startDestination = BottomBarHomeItem.Home.route)
    {
        composable(route = BottomBarHomeItem.Home.route)
        {
            HomeScreen()
        }
    }
}