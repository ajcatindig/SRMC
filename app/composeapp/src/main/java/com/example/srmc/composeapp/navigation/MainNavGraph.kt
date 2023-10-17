package com.example.srmc.composeapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.srmc.composeapp.component.bottombar.BottomBarHomeItem
import com.example.srmc.composeapp.ui.screens.home.AppointmentScreen
import com.example.srmc.composeapp.ui.screens.home.HomeScreen
import com.example.srmc.composeapp.ui.screens.home.ProfileScreen
import com.example.srmc.composeapp.ui.screens.home.TransactionScreen

@Composable
fun MainNavGraph(navController : NavHostController)
{
    NavHost(
            navController = navController,
            route = Graph.HOME,
            startDestination = BottomBarHomeItem.Doctors.route)
    {
        composable(route = BottomBarHomeItem.Doctors.route)
        {
            HomeScreen(viewModel = hiltViewModel(),
            onNavigateToDoctorDetail = {})
        }

        composable(route = BottomBarHomeItem.Appointments.route)
        {
            AppointmentScreen(viewModel = hiltViewModel())
        }

        composable(route = BottomBarHomeItem.Transactions.route)
        {
            TransactionScreen(viewModel = hiltViewModel())
        }

        composable(route = BottomBarHomeItem.Profile.route)
        {
            ProfileScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLogin = {
                        navController.popBackStack()
                        navController.navigate(Graph.AUTHENTICATION)
                    },
                    onAboutAppClick = {})
        }
    }
}