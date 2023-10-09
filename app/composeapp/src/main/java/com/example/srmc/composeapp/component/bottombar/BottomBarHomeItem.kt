package com.example.srmc.composeapp.component.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.PermContactCalendar
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarHomeItem(
        val route : String,
        val title : String,
        val icon : ImageVector
)
{
    object Doctors : BottomBarHomeItem(
            route = "DOCTORS",
            title = "Doctors",
            icon = Icons.Filled.MedicalServices)

    object Appointments : BottomBarHomeItem(
            route = "APPOINTMENTS",
            title = "Appointments",
            icon = Icons.Filled.PermContactCalendar)

    object Transactions : BottomBarHomeItem(
            route = "TRANSACTIONS",
            title = "Transactions",
            icon = Icons.Filled.Payment)

    object Profile : BottomBarHomeItem(
            route = "PROFILE",
            title = "Profile",
            icon = Icons.Filled.AccountCircle)
}
