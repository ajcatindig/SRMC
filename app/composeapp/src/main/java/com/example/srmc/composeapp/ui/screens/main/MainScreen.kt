package com.example.srmc.composeapp.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.srmc.composeapp.component.bottombar.BottomBarHomeItem
import com.example.srmc.composeapp.navigation.MainNavGraph

@Composable
fun MainScreen(navController : NavHostController = rememberNavController())
{
    val scaffoldState = rememberScaffoldState()
    Scaffold(bottomBar = { BottomBar(navController = navController) },
             scaffoldState = scaffoldState) {
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController : NavHostController) {
    val screens = listOf(
            BottomBarHomeItem.Doctors,
            BottomBarHomeItem.Appointments,
            BottomBarHomeItem.Transactions,
            BottomBarHomeItem.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarHeight = 60.dp

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(backgroundColor = MaterialTheme.colors.surface ,
                         contentColor = Color(0xff15C3DD) ,
                         elevation = 0.dp ,
                         modifier = Modifier
                                 .fillMaxWidth()
                                 .height(bottomBarHeight)) {
            screens.forEach { screen ->
                AddItem(screen = screen ,
                        currentDestination = currentDestination ,
                        navController = navController)
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
        screen: BottomBarHomeItem ,
        currentDestination: NavDestination? ,
        navController: NavHostController
                    ) {
    BottomNavigationItem(
            icon = { Icon(imageVector = screen.icon , contentDescription = "") } ,
            selected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true ,
            unselectedContentColor = Color(0xff15C3DD).copy(0.4f) ,
            onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            } ,
            modifier = Modifier.navigationBarsPadding().align(CenterVertically),
            alwaysShowLabel = false)
}