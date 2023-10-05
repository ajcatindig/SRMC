package com.example.srmc.composeapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.srmc.composeapp.ui.screens.auth.ForgotScreen
import com.example.srmc.composeapp.ui.screens.auth.LoginScreen
import com.example.srmc.composeapp.ui.screens.auth.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController : NavController)
{
    navigation(route = Graph.AUTHENTICATION, startDestination = AuthScreen.Login.route)
    {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                    viewModel = hiltViewModel() ,
                    onNavigateToSignUp = { navController.navigate(AuthScreen.SignUp.route) },
                    onNavigateToHome = { navController.popBackStack()
                        navController.navigate(Graph.HOME)},
                    onNavigateToForgot = {navController.navigate(AuthScreen.Forgot.route)})
        }
        composable(route = AuthScreen.SignUp.route) {
            RegisterScreen(
                    onNavigateUp = { navController.navigateUp()},
                    viewModel = hiltViewModel())
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotScreen(onNavigateUp = { navController.navigateUp()},
                         viewModel = hiltViewModel())
        }

    }
}


sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")

}