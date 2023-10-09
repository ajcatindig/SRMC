package com.example.srmc.composeapp.component.scaffold.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.srmc.composeapp.ui.theme.typography

@Composable
fun ProfileTopBar(title : String = "Profile")
{
    TopAppBar(
            title = {
                Row {
                    Text(text = title ,
                         textAlign = TextAlign.Center ,
                         color = MaterialTheme.colors.onPrimary ,
                         modifier = Modifier
                                 .fillMaxWidth() ,
                         style = typography.h5)
                }
            } ,
            backgroundColor = MaterialTheme.colors.surface ,
            elevation = 0.dp
             )
}