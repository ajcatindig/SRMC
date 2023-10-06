package com.example.srmc.composeapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen()
{
    Column(Modifier.fillMaxSize()) {
        Text(text = "APPOINTMENT SCREEN", textAlign = TextAlign.Center, color = Color.Black, fontSize = 35.sp)
    }
}

@Composable
fun HomeContent()
{
}