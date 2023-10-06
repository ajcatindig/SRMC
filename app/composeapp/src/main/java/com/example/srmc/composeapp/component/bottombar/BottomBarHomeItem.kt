package com.example.srmc.composeapp.component.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermContactCalendar
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarHomeItem(
        val route : String,
        val title : String,
        val icon : ImageVector
)
{
    object Home : BottomBarHomeItem(
            route = "HOME",
            title = "Home",
            icon = Icons.Filled.Home
                                   )
}
