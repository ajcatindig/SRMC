package com.example.srmc.composeapp.component.scaffold.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentDetailTopBar(
        title : String = "Appointment Detail",
        onNavigateUp : (() -> Unit)? = null)
{
    TopAppBar(
            title = {
                Row {
                    Text(text = title ,
                         textAlign = TextAlign.Center ,
                         color = MaterialTheme.colors.onPrimary ,
                         modifier = Modifier.fillMaxWidth() ,
                         style = MaterialTheme.typography.h5)
                }
            } ,
            navigationIcon = onNavigateUp?.let {
                val navIcon : @Composable () -> Unit = {
                    IconButton(
                            onClick = onNavigateUp,
                            modifier = Modifier.padding(start = 4.dp)) {
                        Icon(
                                imageVector = Icons.Filled.ArrowBackIos ,
                                contentDescription = "Back" ,
                                tint = MaterialTheme.colors.onPrimary)
                    }
                }
                navIcon
            } ,
            backgroundColor = MaterialTheme.colors.surface ,
            contentColor = MaterialTheme.colors.onPrimary ,
            elevation = 0.dp)
}